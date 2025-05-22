package com.rinsey24.needasavior.Adapter; // Убедитесь, что имя пакета верное

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rinsey24.needasavior.CartItem; // Импорт вашего CartItem
import com.rinsey24.needasavior.R;       // Импорт ресурсов (убедитесь, что R.layout.cart_item и R.id.* существуют)

import java.util.ArrayList;
import java.util.List;
import java.util.Locale; // Для String.format с учетом локали

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private final List<CartItem> allCartItems; // Полный список всех товаров, включая "мягко удаленные"
    private List<CartItem> displayedItems;    // Отфильтрованный список товаров для отображения в RecyclerView
    private final LayoutInflater inflater;
    private final Context context;
    private final OnCartItemActionListener listener;

    /**
     * Интерфейс для обработки действий с элементами корзины из Activity/Fragment.
     */
    public interface OnCartItemActionListener {
        /**
         * Вызывается при изменении количества товара.
         * @param item товар, у которого изменилось количество.
         * @param newQuantity новое количество.
         */
        void onQuantityChange(CartItem item, int newQuantity);

        /**
         * Вызывается при запросе на "мягкое" удаление товара.
         * Activity/Fragment должен будет установить флаг isEffectivelyVisible = false
         * и затем вызвать adapter.updateDisplayedListAndNotify().
         * @param item товар для "мягкого" удаления.
         */
        void onItemRemoved(CartItem item);
    }

    public CartAdapter(List<CartItem> allCartItems, Context context, OnCartItemActionListener listener) {
        this.allCartItems = allCartItems; // Сохраняем ссылку на полный список
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.listener = listener;
        this.displayedItems = new ArrayList<>();
        updateDisplayedListAndNotify(); // Первоначальное формирование списка для отображения
    }

    /**
     * Обновляет внутренний список отображаемых элементов (displayedItems)
     * на основе флага isEffectivelyVisible из полного списка (allCartItems)
     * и уведомляет RecyclerView о необходимости перерисовки.
     */
    public void updateDisplayedListAndNotify() {
        displayedItems.clear();
        if (this.allCartItems != null) {
            for (CartItem item : this.allCartItems) {
                if (item.isEffectivelyVisible()) {
                    displayedItems.add(item);
                }
            }
        }
        notifyDataSetChanged(); // Уведомляем RecyclerView о полном изменении данных.
        // Для улучшения производительности на больших списках рассмотрите DiffUtil.
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cart_item, parent, false);
        // Убедитесь, что R.layout.cart_item - это ваш XML-файл для элемента списка корзины
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Работаем с displayedItems для отображения
        // holder.getAdapterPosition() более безопасен внутри слушателей, если позиция может измениться
        // но здесь 'position' корректна для текущей привязки.
        CartItem item = displayedItems.get(position);

        holder.titleTextView.setText(item.getTitle());
        // Используем Locale.getDefault() для корректного форматирования десятичных чисел
        holder.priceTextView.setText(String.format(Locale.getDefault(), "%.2f", item.getPrice())); // Цена за единицу
        holder.quantityTextView.setText(String.valueOf(item.getQuantity()));
        holder.totalPriceTextView.setText("Итого: " + String.format(Locale.getDefault(), "%.2f", item.getTotalPrice()));

        // Загрузка изображения (логика из вашего первоначального кода)
        String imageName = item.getImageName();
        if (imageName != null && !imageName.isEmpty()) {
            int imageResId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
            if (imageResId != 0) {
                holder.productImage.setImageResource(imageResId);
            } else {
                // TODO: Замените R.drawable.placeholder_image на ваш реальный плейсхолдер, если он есть
                // Например, holder.productImage.setImageResource(R.drawable.default_product_image);
                // Если плейсхолдера нет, можно скрыть ImageView или оставить стандартный фон
                holder.productImage.setImageResource(R.drawable.placeholder_image); // ЗАМЕНИТЕ ЭТО
            }
        } else {
            // TODO: Замените R.drawable.placeholder_image на ваш реальный плейсхолдер
            holder.productImage.setImageResource(R.drawable.placeholder_image); // ЗАМЕНИТЕ ЭТО
        }

        // Обработчик для кнопки уменьшения количества
        holder.decreaseButton.setOnClickListener(v -> {
            int currentAdapterPosition = holder.getAdapterPosition();
            // Проверка, что позиция все еще действительна
            if (currentAdapterPosition == RecyclerView.NO_POSITION) {
                return;
            }
            // Получаем элемент из displayedItems, но это ссылка на тот же объект, что и в allCartItems
            CartItem currentItem = displayedItems.get(currentAdapterPosition);
            int currentQuantity = currentItem.getQuantity();

            if (currentQuantity > 1) { // Не позволяем количеству стать меньше 1
                int newQuantity = currentQuantity - 1;
                currentItem.setQuantity(newQuantity); // Обновляем количество в объекте CartItem

                // Обновляем TextView для количества и итоговой цены немедленно
                holder.quantityTextView.setText(String.valueOf(newQuantity));
                holder.totalPriceTextView.setText("Итого: " + String.format(Locale.getDefault(), "%.2f", currentItem.getTotalPrice()));

                // Уведомляем RecyclerView об изменении только этого элемента
                notifyItemChanged(currentAdapterPosition);

                // Вызываем слушателя, если Activity/Fragment должен отреагировать (например, обновить общую сумму)
                if (listener != null) {
                    listener.onQuantityChange(currentItem, newQuantity);
                }
            }
            // Можно добавить логику для currentQuantity == 1, если нужно удалять товар или показывать предупреждение
        });

        // Обработчик для кнопки увеличения количества
        holder.increaseButton.setOnClickListener(v -> {
            int currentAdapterPosition = holder.getAdapterPosition();
            if (currentAdapterPosition == RecyclerView.NO_POSITION) {
                return;
            }
            CartItem currentItem = displayedItems.get(currentAdapterPosition);
            int newQuantity = currentItem.getQuantity() + 1;
            // Здесь можно добавить проверку на максимальное количество, если это необходимо
            currentItem.setQuantity(newQuantity);

            holder.quantityTextView.setText(String.valueOf(newQuantity));
            holder.totalPriceTextView.setText("Итого: " + String.format(Locale.getDefault(), "%.2f", currentItem.getTotalPrice()));

            notifyItemChanged(currentAdapterPosition);

            if (listener != null) {
                listener.onQuantityChange(currentItem, newQuantity);
            }
        });

        // Обработчик для кнопки "удаления" (мягкого удаления)
        holder.deleteButton.setOnClickListener(v -> {
            int currentAdapterPosition = holder.getAdapterPosition();
            if (currentAdapterPosition == RecyclerView.NO_POSITION) {
                return;
            }
            CartItem itemToSoftDelete = displayedItems.get(currentAdapterPosition);

            if (listener != null) {
                // Передает элемент слушателю.
                // Activity установит флаг isEffectivelyVisible = false
                // и затем вызовет cartAdapter.updateDisplayedListAndNotify();
                listener.onItemRemoved(itemToSoftDelete);
            }
        });
    }

    @Override
    public int getItemCount() {
        return displayedItems.size(); // Возвращает количество отображаемых элементов
    }

    /**
     * ViewHolder для элементов корзины.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView titleTextView, priceTextView, quantityTextView, totalPriceTextView;
        Button decreaseButton, increaseButton, deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.productImageCart);
            titleTextView = itemView.findViewById(R.id.titleTextViewCart);
            priceTextView = itemView.findViewById(R.id.titleTextViewCartPrice); // Цена за единицу
            quantityTextView = itemView.findViewById(R.id.quantityTextViewCart);
            totalPriceTextView = itemView.findViewById(R.id.TotalPricetextViewCart); // Итоговая цена для элемента
            decreaseButton = itemView.findViewById(R.id.decreaseButton);
            increaseButton = itemView.findViewById(R.id.increaseButton);
            deleteButton = itemView.findViewById(R.id.deleteButtonCart);
        }
    }
}