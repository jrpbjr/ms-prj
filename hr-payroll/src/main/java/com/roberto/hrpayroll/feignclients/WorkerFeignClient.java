package com.roberto.hrpayroll.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@FeignClient(name = "hr-worker", url = "http://localhost:8001", path = "/workers")
public class WorkerFeignClient {

}
