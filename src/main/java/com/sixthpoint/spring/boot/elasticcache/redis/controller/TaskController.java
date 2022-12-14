package com.sixthpoint.spring.boot.elasticcache.redis.controller;

import com.sixthpoint.spring.boot.elasticcache.redis.controller.util.ResponseUtil;
import com.sixthpoint.spring.boot.elasticcache.redis.service.TaskService;
import com.sixthpoint.spring.boot.elasticcache.redis.service.dto.TaskDTO;
import io.swagger.annotations.ApiOperation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping( value = "/longRunningTask" )
public class TaskController {

    @Autowired
    private TaskService taskService;
    
    private static final Logger logger = LogManager.getLogger(TaskController.class);

    @ApiOperation(
            value = "Perform the long running task")
    @RequestMapping(value = "/{seconds}", method = RequestMethod.GET)
    public ResponseEntity<TaskDTO> longRunningTask(@PathVariable long seconds) {
        Optional<TaskDTO> user = taskService.getLongRunningTaskResult(seconds);
        logger.info("in longRunningTask().....");
        return ResponseUtil.wrapOrNotFound(user);
    }

    @ApiOperation(
            value = "Resets the cache for a key")
    @RequestMapping(value = "/reset/{seconds}", method = RequestMethod.DELETE)
    public ResponseEntity<?> reset(@PathVariable long seconds) {
    	logger.info("in reset().....");
        taskService.resetLongRunningTaskResult(seconds);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


}
