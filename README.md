# Java Producer Consumer Test #

The main objective was to allow the producer/consumers to work in parallel. In order to achieve this a synchronization mechanism was put in place, based on shared queues and synchronization objects.

The synchronization is based on Queues (ConcurrentLinkedQueue implementation), and sync objects all initialized in the main thread.

The classes in place are:
## QueueProducer ##
This is the main producer. It needs the input paragraph, the output queues (consumer1 and consumer 2) and some synchronization objects for initialization.

## QueueWordConsumer ##
This is a word consumer. It appends a number (it's id) in all supplied words. For initialization, sync object with producer and final consumer should be supplied, along with the input and output queues. 

## QueueFinalConsumer ##
The final consumer has some sync objects for the consumers to allow notifying whether the input is completed.


