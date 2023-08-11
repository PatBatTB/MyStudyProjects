package com.github.patbattb.hw2.service;

import org.junit.jupiter.api.BeforeEach;

public final class DefaultTaskManagerTest extends TaskManagerTest<DefaultTaskManager> {

    @Override
    @BeforeEach
    void setUp() {
        super.setUp();
        taskManager = new DefaultTaskManager();
    }
}
