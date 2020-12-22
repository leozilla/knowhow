AWS
===

## Regions and Zones

These locations are composed of Regions, Availability Zones, Local Zones, AWS Outposts, and Wavelength Zones.
Each Region is a separate geographic area.

* Availability Zones are multiple, isolated locations within each Region.
* Local Zones provide you the ability to place resources, such as compute and storage, in multiple locations closer to your end users.
* AWS Outposts brings native AWS services, infrastructure, and operating models to virtually any data center,
  co-location space, or on-premises facility.
* Wavelength Zones allow developers to build applications that deliver ultra-low latencies to 5G devices and end users.
  Wavelength deploys standard AWS compute and storage services to the edge of telecommunication carriers' 5G networks.

### Regions

AWS has the concept of a Region, which is a physical location around the world where we cluster data centers.
We call each group of logical data centers an Availability Zone.

Each Amazon EC2 Region is designed to be isolated from the other Amazon EC2 Regions.
This achieves the greatest possible fault tolerance and stability.

* us-east-2	US East (Ohio)
* us-east-1	US East (N. Virginia)

### Availability Zones

Each Region has multiple, isolated locations known as Availability Zones.
An Availability Zone (AZ) is one or more discrete data centers with redundant power, networking, and connectivity in an AWS Region.

You can also use Elastic IP addresses to mask the failure of an instance in one Availability Zone
by rapidly remapping the address to an instance in another Availability Zone.

For example, the Availability Zone us-east-1a for your AWS account might not be the same location as us-east-1a for another AWS account.
To coordinate Availability Zones across accounts, you must use the AZ ID, which is a unique and consistent identifier for an Availability Zone.