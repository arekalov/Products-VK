# Products-VK
## **Artem Rekalov**
#### Это тестовое задание для прохождения на стажировку в VK (направление Android-разработчик)

### Было сделано
1. Основная часть задания с пагинацией по 20 элементов
2. Переход на детальный экран товара (дополнительно видны поля brand, price и каруселька с Images)
3. Поиск товара в бэкенде с последующей возможностью перехода на экран товара 
4. Вкладка с категориями товаров с возможностью просмотра товаров в данной категории
5. Обработка потери подключения

### Стек
- Kotlin
- Android navigation
- Paging 3 
- Kotlin Coroutines
- Retrofit
- Glidea
- ssp, sdp <https://github.com/intuit/sdp> (лучшая адаптация приложения к разным размерам экрана)

### Архитектура 
1. App (точка входа в приложение, корневой навигационный граф, MainActivity)
2. Presentation (UI слой со всеми фрагментами, View моделями и адаптерами)
3. Data (подключение к серверу, POJO, PagingSource)

### Особенности реализации
- Clean Architecture
- MVVM
- Single Activity
- Навигация с помощью navigation components + BottomNavigationView
- Пагинация с помощью Paging 3
- Cotlin Coroutines + LiveData
- Темная тема
- Красивая иконка

> PS А на стажировку то хочется)))