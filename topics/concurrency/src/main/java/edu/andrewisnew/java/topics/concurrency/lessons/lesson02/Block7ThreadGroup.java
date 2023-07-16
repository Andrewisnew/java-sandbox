package edu.andrewisnew.java.topics.concurrency.lessons.lesson02;

public class Block7ThreadGroup {
    public static void main(String[] args) {
        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
        System.out.println(threadGroup.getName()); //main
        System.out.println(threadGroup.getMaxPriority()); //10
        System.out.println(threadGroup.getParent()); //system group
        System.out.println(threadGroup.getParent().getParent()); //null. System group - корень

        System.out.println(threadGroup.activeCount()); //количество живых потоков
        System.out.println(threadGroup.activeGroupCount()); //количество дочерних групп с >= 1 живым потоком

        threadGroup.setMaxPriority(Thread.NORM_PRIORITY); //если в группу добавить потом с большим приоритетом, приоритет потока будет понижен до указанного

        //threadGroup.destroy(); exception в группеесть живые потоки
        ThreadGroup myGroup = new ThreadGroup(threadGroup, "myGroup");
        new Thread(myGroup, () -> System.out.println("Hello"));
        myGroup.interrupt(); //interrupt всем потокам
        myGroup.destroy();
        //new Thread(myGroup, () -> System.out.println("Hello")); exception группа уничтожена

        //еще можно унаследоваться от ThreadGroup переопределив uncaughtException
    }
}
