import helper.WordWithOrder;
import queue.QueueFinalConsumer;
import queue.QueueProducer;
import queue.QueueWordConsumer;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting app...");

        final String paragraph = "Java is a general-purpose computer programming language that is concurrent, " +
                "class-based, object-oriented,[14] and specifically designed to have as few implementation dependencies " +
                "as possible. It is intended to let application developers \"write once, run anywhere\" (WORA),[15] " +
                "meaning that compiled Java code can run on all platforms that support Java without the need for " +
                "recompilation.[16] Java applications are typically compiled to bytecode that can run on any Java " +
                "virtual machine (JVM) regardless of computer architecture. As of 2016, Java is one of the most " +
                "popular programming languages in use,[17][18][19][20] particularly for client-server web applications, " +
                "with a reported 9 million developers.[21] Java was originally developed by James Gosling at Sun " +
                "Microsystems (which has since been acquired by Oracle Corporation) and released in 1995 as a core " +
                "component of Sun Microsystems' Java platform. The language derives much of its syntax from C and C++, " +
                "but it has fewer low-level facilities than either of them.\n" +
                "The original and reference implementation Java compilers, virtual machines, and class libraries were " +
                "originally released by Sun under proprietary licenses. As of May 2007, in compliance with the " +
                "specifications of the Java Community Process, Sun relicensed most of its Java technologies under " +
                "the GNU General Public License. Others have also developed alternative implementations of these " +
                "Sun technologies, such as the GNU Compiler for Java (bytecode compiler), GNU Classpath (standard " +
                "libraries), and IcedTea-Web (browser plugin for applets).\n" +
                "The latest version is Java 8 which is the only version currently supported for free by Oracle, " +
                "although earlier versions are supported both by Oracle and other companies on a commercial basis.";

        final Map<Integer, Boolean> finishedConsumers = new ConcurrentHashMap<>();
        final Queue<WordWithOrder> finalConsumerInput = new ConcurrentLinkedQueue<>();
        final Queue<WordWithOrder> consumer1Input = new ConcurrentLinkedQueue<>();
        final Queue<WordWithOrder> consumer2Input = new ConcurrentLinkedQueue<>();
        final AtomicBoolean inputComplete = new AtomicBoolean(false);

        final Object consumer1SyncObject = new Object();
        final Object consumer2SyncObject = new Object();
        final Object finalConsumerSyncObject = new Object();

        final QueueFinalConsumer finalConsumer = new QueueFinalConsumer(finishedConsumers, finalConsumerInput, finalConsumerSyncObject);
        final QueueWordConsumer wordConsumer1 = new QueueWordConsumer(1, consumer1Input, finalConsumerInput, inputComplete, finishedConsumers, finalConsumerSyncObject, consumer1SyncObject);
        final QueueWordConsumer wordConsumer2 = new QueueWordConsumer(2, consumer2Input, finalConsumerInput, inputComplete, finishedConsumers, finalConsumerSyncObject, consumer2SyncObject);
        final QueueProducer producer = new QueueProducer(inputComplete, consumer1Input, consumer2Input, paragraph, consumer1SyncObject, consumer2SyncObject);

        finalConsumer.start();
        wordConsumer1.start();
        wordConsumer2.start();
        producer.start();

        System.out.println("Done!");
    }
}