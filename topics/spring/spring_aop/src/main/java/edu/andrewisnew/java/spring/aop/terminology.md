Aspect - класс с аннотацией @Aspect. Содержит код, который будет
встраиваться.
Weaving - процесс оборачивания аспектом
Join point - место, куда будет встраиваться аспект. Это метод
Advice - действие, которое выполнит аспект в Join point. 
Бывает Before, After returning, After throwing, After, Around
Pointcut - предикат, указывающий в какой join point встроить
Aop proxy - обёрнутый аспектом класс