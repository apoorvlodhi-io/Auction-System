package com.arango.auction.service.impl;

import com.google.cloud.tasks.v2.*;

import java.io.IOException;

import com.google.protobuf.Timestamp;

public class CreateTask {

    private static final String projectId = "new-tasks-388306";
    private static final String location = "asia-south1";
    private static final String queueName = "my-queue";

    public static void createAuctionTask(String actionType,Long auctionId, Timestamp endTime)
            throws IOException {
        try (CloudTasksClient client = CloudTasksClient.create()) {
            String queuePath = QueueName.of(projectId, location, queueName).toString();
            String url = "/auction/" + actionType + "?auctionId=" + auctionId;
            System.out.println("Url: " + url);
            Task.Builder taskBuilder =
                    Task.newBuilder()
                            .setAppEngineHttpRequest(
                                    AppEngineHttpRequest.newBuilder()
                                            .setRelativeUri(url)
                                            .setHttpMethod(HttpMethod.POST)
                                            .build());
            taskBuilder.setScheduleTime(endTime);

            Task task = client.createTask(queuePath, taskBuilder.build());
            System.out.println("Task created: " + task.getName());
        }
    }
}
