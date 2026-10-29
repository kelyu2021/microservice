package com.demo.consumer.controller;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ConsumerController {

    private final RestTemplate restTemplate;
    private final DiscoveryClient discoveryClient;

    public ConsumerController(RestTemplate restTemplate, DiscoveryClient discoveryClient) {
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;
    }

    /**
     * Call provider via service name (load-balanced, resolved by Nacos).
     * No hardcoded host:port needed.
     */
    @GetMapping("/call/{name}")
    public String callProvider(@PathVariable String name) {
        return restTemplate.getForObject("http://nacos-provider/hello/" + name, String.class);
    }

    @GetMapping("/call")
    public String callProvider() {
        return restTemplate.getForObject("http://nacos-provider/hello", String.class);
    }

    /**
     * List all registered instances of the provider from Nacos.
     */
    @GetMapping("/instances")
    public List<String> listInstances() {
        return discoveryClient.getInstances("nacos-provider")
                .stream()
                .map(ServiceInstance::getUri)
                .map(Object::toString)
                .collect(Collectors.toList());
    }

    /**
     * List all services currently registered in Nacos.
     */
    @GetMapping("/services")
    public List<String> listServices() {
        return discoveryClient.getServices();
    }
}
