package com.gainsight.rabbitmq_demo_project;

import lombok.Data;

public @Data class SimpleMessage {

    private String name;
    private String desc;
}