# java-weakreference-simple-demo
This is a small demonstration using WeakReference

# Inspired by
- Declaring delegates (the iOS name for listeners) as "weak var"

In Swift you find in event emitters:

        weak var delegate : Delegate?

In Java, and especially in Android, we don't find these in event emitters. This makes for example, if you have an
activity listening to a global listener (for example from databinding) outside it, the activity is theoretically STILL
IN MEMORY EVEN IT IS NOT USED UNTIL REMOVED BY THE PROGRAMMER HIMSELF. This is not the case with WeakReference.

# Trying it

- Just try it using Java or any Java IDE (Eclipse, Netbeans, etc...)
- Also try to change WeakReference into SoftReference and see the difference.
