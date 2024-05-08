# Products-VK
## **Artem Rekalov**
#### Тестовое задание для прохождения на стажировку в VK (направление Android-разработчик в Mail.ru)

### Было сделано
1. Основная часть задания с пагинацией по 20 элементов
2. Переход на детальный экран товара (дополнительно видны поля brand, price и каруселька с Images)
3. Поиск товара в бэкенде с последующей возможностью перехода на экран товара 
4. Вкладка с категориями товаров с возможностью просмотра товаров в данной категории
5. Обработка потери подключения

### Стек
- Kotlin
- Android navigation
- Paging3 
- Kotlin Coroutines
- Retrofit
- Dagger2
- Glide
- ssp, sdp <https://github.com/intuit/sdp> (лучшая адаптация приложения к разным размерам экрана)

### Архитектура 
1. App (основная логика приложения)
2. Data (подключение к серверу, POJO, PagingSource)

### Особенности реализации
- Clean Architecture
- MVVM
- Single Activity
- Навигация с помощью navigation components + BottomNavigationView
- Пагинация с помощью Paging 3
- Kotlin Coroutines + LiveData
- Progress bars
- Темная тема
- Красивая иконка