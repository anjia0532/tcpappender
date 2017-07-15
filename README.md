# tcpappender
This application was generated using JHipster 4.6.1, you can find documentation and help at [https://jhipster.github.io/documentation-archive/v4.6.1](https://jhipster.github.io/documentation-archive/v4.6.1).

# elk
start elasticsearch(`localhost:9200`),kibana(`localhost:5601`)

## logstash.conf

```
input {
    tcp {
        port => 5000
        codec => json_lines
    }
}

filter {
    if [logger_name] =~ "metrics" {
        kv {
            source => "message"
            field_split => ", "
            prefix => "metric_"
        }
	mutate {
          convert => { "metric_value" => "float" }
          convert => { "metric_count" => "integer" }
          convert => { "metric_min" => "float" }
          convert => { "metric_max" => "float" }
          convert => { "metric_mean" => "float" }
          convert => { "metric_stddev" => "float" }
          convert => { "metric_median" => "float" }
          convert => { "metric_p75" => "float" }
          convert => { "metric_p95" => "float" }
          convert => { "metric_p98" => "float" }
          convert => { "metric_p99" => "float" }
          convert => { "metric_p999" => "float" }
          convert => { "metric_mean_rate" => "float" }
          convert => { "metric_m1" => "float" }
          convert => { "metric_m5" => "float" }
          convert => { "metric_m15" => "float" }
        }
    }
    mutate {
    	add_field => { "instance_name" => "%{app_name}-%{host}:%{app_port}" }
    }
}

output {
    elasticsearch {
        hosts => ["localhost:9200"]
    }
    stdout { codec => rubydebug }
}
```
start logstash
```bash
logstash -f /path/to/logstash.conf
```

# start application

```bash
./mvnw
```
```
----------------------------------------------------------
	Application 'tcpappender' is running! Access URLs:
	Local: 		http://localhost:8080
	External: 	http://192.168.1.124:8080
	Profile(s): 	[swagger, dev]
----------------------------------------------------------
```

```bash
curl http://localhost:8080/api/tcp?str=jHipster&size=50000&padStr=

#str:the String to pad out, may be null
#size:the size to pad to
#padStr:the String to pad with, null or empty treated as param `str`
```

```java

//...
    @GetMapping("/tcp")
    public String getActiveProfiles(@RequestParam String str,@RequestParam int size,@RequestParam(required=false) String padStr) {
    	String leftPadStr= StringUtils.leftPad(str, size, StringUtils.defaultIfBlank(padStr, str));
    	log.info(leftPadStr);
        return MessageFormat.format("str:{0},pad size:{1,number},pad str:{2},result str:{3}", str,size,StringUtils.defaultString(padStr, str),leftPadStr);
    }
//...
```
[TcpAppenderResource.java#L23-L26](https://github.com/anjia0532/tcpappender/blob/master/src/main/java/com/anjia/tcpappender/web/rest/TcpAppenderResource.java#L23-L26)


![](https://raw.githubusercontent.com/anjia0532/tcpappender/master/snipaste20170715_230147.png)
