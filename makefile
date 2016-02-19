JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
DNSCacheManager.java \
DNSFeedback.java \
DNSHeader.java \
DNSListener.java \
DNSQuestion.java \
DNSResponse.java \
DNSServerTest.java \
DNSUtility.java \
ReplicaPicker.java \
DNSServer.java

default: classes
classes: $(CLASSES:.java=.class)
clean:
	rm -f *.class

