package com.samplespringboot.topic;

import com.samplespringboot.core.BaseEntity;

import javax.persistence.Entity;

@Entity
public class Topic extends BaseEntity {

    private String topicName;
    private int questionCount;

    protected Topic() {
        super();
    }

    public Topic(String topicName, int questionCount) {
        this();
        this.topicName = topicName;
        this.questionCount = questionCount;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public int getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }
}
