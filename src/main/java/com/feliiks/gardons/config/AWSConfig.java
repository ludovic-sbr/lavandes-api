package com.feliiks.gardons.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSConfig {

    @Value("${amazon.aws.accessKey}")
    private String amazonAccessKey;

    @Value("${amazon.aws.secretKey}")
    private String amazonSecretKey;

    public AWSCredentials credentials() {
        return new BasicAWSCredentials(
                amazonAccessKey,
                amazonSecretKey
        );
    }

    @Bean
    public AmazonS3 amazonS3() {
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials()))
                .withRegion(Regions.EU_WEST_3)
                .build();
    }

}