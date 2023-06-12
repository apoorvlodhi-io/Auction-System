package com.arango.auction.service.impl;

import com.google.api.client.util.Value;
import com.google.cloud.tasks.v2.*;

import java.io.IOException;

import com.google.protobuf.Timestamp;

public class CreateTask {
    @Value("${gcloud.projectId}")
    private static String projectId;
    @Value("${gcloud.location}")
    private static String location;
    @Value("${gcloud.queueName}")
    private static String queueName; //todo: Move these to config ----- DONE

    public static void createAuctionTask(String actionType, Long auctionId, Timestamp endTime)
            throws IOException {
        try (CloudTasksClient client = CloudTasksClient.create()) {
//            String queuePath = QueueName.of(projectId, location, queueName).toString();
//            String url = "/auction/" + actionType + "?auctionId=" + auctionId;
//            System.out.println("Url: " + url);
//            Task.Builder taskBuilder =
//                    Task.newBuilder()
//                            .setAppEngineHttpRequest(
//                                    AppEngineHttpRequest.newBuilder()
//                                            .setRelativeUri(url)
//                                            .setHttpMethod(HttpMethod.POST)
//                                            .build());
//            taskBuilder.setScheduleTime(endTime);
//
//            Task task = client.createTask(queuePath, taskBuilder.build());
//            System.out.println("Task created: " + task.getName());
            System.out.println("Task created: ");
        }
    }
}
