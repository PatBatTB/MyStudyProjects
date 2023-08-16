package com.github.patbattb.hw2.service;

import com.github.patbattb.hw2.service.manager.DefaultTaskManager;
import org.junit.jupiter.api.BeforeEach;

public final class DefaultTaskManagerTest extends TaskManagerTest<DefaultTaskManager> {

    @Override
    @BeforeEach
    void setUp() {
        super.setUp();
        taskManager = new DefaultTaskManager();
    }
}
