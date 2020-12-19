DNS
===

A DNS name server is a server that stores the DNS records for a domain; a DNS name server responds with answers to queries against its database.
Different users can simultaneously receive different translations for the same domain name, 
this is key to providing faster and more reliable responses on the Internet and is widely used by most major Internet services.

In practice caching is used in DNS servers to off-load the root servers, and as a result, root name servers actually are involved in only a relatively small fraction of all requests.
To improve efficiency, reduce DNS traffic across the Internet, and increase performance in end-user applications, 
the Domain Name System supports DNS cache servers which store DNS query results for a period of time determined in the configuration (time-to-live) of the domain name record in question.
The TTL period of validity may vary from a few seconds to days or even weeks.
Internet service providers typically provide recursive and caching name servers for their customers.

DNS requires that caching nameservers remember negative responses as well as positive ones. 
If an authoritative nameserver returns a negative response, indicating that a name does not exist, this is cached.

A reverse DNS lookup is a query of the DNS for domain names when the IP address is known.

Multiple hostnames may correspond to a single IP address, which is useful in virtual hosting, in which many web sites are served from a single host.
Alternatively, a single hostname may resolve to many IP addresses to facilitate fault tolerance and load distribution to multiple server instances across an enterprise or the global Internet.

DNS primarily uses the User Datagram Protocol (UDP) on port number 53 to serve requests.

Normally DNS name servers are set up in clusters. The database within each cluster is synchronized through zone transfers. 
The SOA record for a zone contains data to control the zone transfer.

[Record types](https://en.wikipedia.org/wiki/List_of_DNS_record_types#A)

 * __SOA__: contain administrative info about the zone, especially regarding zone transfers.
 * __A__: Returns a 32-bit IPv4 address, most commonly used to map hostnames to an IP address of the host.
 * __AAAA__: Returns a 128-bit IPv6 address, most commonly used to map hostnames to an IP address of the host.  
 * __NSv: Delegates a DNS zone to use the given authoritative name servers
 * __PTR__: Pointer to a canonical name. Unlike a CNAME, DNS processing stops and just the name is returned. 
   The most common use is for implementing reverse DNS lookups, but other uses include such things as DNS-SD.
 * __CNAME__: Alias of one name to another: the DNS lookup will continue by retrying the lookup with the new name.
   One can, for example, point ftp.example.com and www.example.com to the DNS entry for example.com, 
   which in turn has an A record which points to the IP address.
 * __TXT__: Originally for arbitrary human-readable text in a DNS record. However this record more often carries machine-readable data.
 * __SRV__: Defining the location, i.e., the hostname and port number, of servers for specified services.