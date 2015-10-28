# jAtlasX - Access RIPE Atlas through Java
jAtlasX is a Java toolset for creating trace route measurements on the famous [RIPE Atlas](https://atlas.ripe.net) platform. jAtlasX also includes features to process the trace routes on a hop-by-hop basis (e.g. IP matching). The project is easy to extended for supporting additional RIPE Atlas features. 

In particular jAtlasX has the following capabilities:

1. Create trace route measurements
2. Query probe information by ASN 
3. Parse JSON measurement replies


## Usage
Learn how to get started with jAtlasX. For a complete code example take a look at the class *MeasurementExample.java* in *src/test/java/net/decix/jatlasx/test/*

### Installation
jAtlasX is distributed as a maven project. You can just check it out and import it into your IDE of choice. It can be build with Java v1.8. It might be compatible with early versions as well, since no feature specific to v1.8 is used. For getting started with how to use individual classes have a look at the tests included into the project. 

If you have not worked with RIPE Atlas before, have a look at their [documentation](https://atlas.ripe.net/docs/) first.

### Create a Traceroute Measurement
Do you want to start right a way with submitting your first measurement? Try calling *createMeasurement* for this. All it needs is an API key for RIPE Atlas to get started. Under the hood jAtlasX takes care of the [rate limits](https://atlas.ripe.net/docs/udm/#rate-limits) applied by RIPE Atlas.  

```java
public class TracerouteMeasurement extends Measurement {

...
public void createMeasurement(String probeId, String targetIp, String description)
...
}
```

#### Get your API key
In order to submit a user-generated measurement to RIPE Atlas, a user needs a valid [API key](https://atlas.ripe.net/docs/keys/) and enough [credits](https://atlas.ripe.net/docs/credits/). 


### Response handler
jAtlasX provides a number of implemented response handlers, to ease the task of parsing the RIPE Atlas response. The following handlers are available:

1. *MeasurementIDHandler* - returns the measurement ID for a user created measurement
2. *ProbeListHandler* - returns a list of Probes for a given ASN
3. *TracerouteHandler* - returns a list of trace route pathes 

#### Write your own handler
You can't find a handler that suits your requirements? You can easily write your own. Just follow the ResponseHandler interface:

```java
public interface ResponseHandler<T> {

	public List<T> handleResponse(String json);
	
}
```
## Lots of measurements
jAtlasX is designed to submit and retrieve a large number of measurements at once. Therefore, it ships with classes ready to process csv files for input and output processing. Have a look at those classes and make good use of it for your convenient start of a large-scale experiment potentially including a large number of measurements.

## Dependencies
jAtlasX is built upon available libraries. Those libraries include the following:

* [Apache Commons CSV](https://commons.apache.org/proper/commons-csv/)
* [Apache Commons Net](https://commons.apache.org/proper/commons-net/)
* [Apache HttpComponents](https://hc.apache.org)
* [json.org](http://www.json.org/java/index.html)

There is no need to get those libraries manually. jAtlasX is distributed as a maven project, thus maven will take care of those dependencies. 

##License: 
jAtlasX is released under the [Apache License v2.0](http://www.apache.org/licenses/LICENSE-2.0.html??). 

## What's next
jAtlasX will be actively maintained and extended in the future. Support for the additional RIPE Atlas features is on our list. We appreciate any requests and feedback. Just contact the authors or directly perfom a pull request. In the meantime we hope that you enjoy working with jAtlasX. 

##Authors:
jAtlasX was developed for a research project at the [DE-CIX Management GmbH](https://www.de-cix.net/). Be invited to reach out to the authors if you want to contribute or have any open questions: 

* Sascha Bleidner (<sascha.bleidner@de-cix.net>)
* Florian Kaufmann (<florian.kaufmann@de-cix.net>)
* Thomas King (<thomas.king@de-cix.net>)


##Alternatives:

jAtlasX is not alone. There are a number of additional projects that interface with RIPE Atlas. An extensive list of alternatives is collected here: 
[ripe-atlas-community-contrib](https://github.com/RIPE-Atlas-Community/ripe-atlas-community-contrib)

## Why Java
There are a number of alternatives written in Python already. If your familiar with Java it will be easy to start with jAtlasX, which is by the time of publication the only toolset for RIPE Atlas written in Java.


## What does X in jAtlasX stands for?
The X in jAtlasX is linked to eXchange, since the initial goal for this project was to identify eXchange points on a given trace route path. 
