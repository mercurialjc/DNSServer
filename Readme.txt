0. How to use
1)Runs the DNSServer: ./dnsserver -p <port> -n <name>
2)The PingServer should be deloped and started on each of the replicas
3)Use dig some way like this: cs5700cdn.example.com -p 40000 @cs5700cdnproject.ccs.neu.edu +time=20


1. High-level approach

DNSServer:
DNSCacheManager.java - Caches previous DNS queries (Not implemented)
DNSFeedback.java - Sends DNS response to the client
DNSHeader.java - Defines the format of DNS header
DNSListener.java - Listens on a specific port, receiving DNS queries over UDP
DNSQuestion.java - Defines the format of DNS question
DNSServer.java - Runs the DNS server
DNSServerTest.java - For testing purpose
DNSUtility.java - Utility functions to help extract information from DNS queries
PingServer.java - Runs on replicas to receive client IP
Pinger.java - Pings the client IP
PingTest - For testing purpose
RepilicaPicker - Picks the best replica

Essentially, the client sends a DNS query to DNSServer, the DNSServer extract the information and analyze it, requesting replicas to ping the client, returning the RTT. Based on that, the "best" replica is thus determined. The DNSServer then build the DNS response, answering the client.

2. Performance enhancing techniques
1) Using scamper -c "ping -c 1" -i ip to to reduce the running time for scamper.
2) Using multithreading technology to avoid waiting time for processes.

3. Challenges
1) Incorrectly binding Socket to local host, which cannot help us establish connection with client.
2) Receiving traffic from planetnet rather than the client's IP, which is initially confusing.
3. Builing the correct DNS response bytes takes time and requires focus and effort to test.