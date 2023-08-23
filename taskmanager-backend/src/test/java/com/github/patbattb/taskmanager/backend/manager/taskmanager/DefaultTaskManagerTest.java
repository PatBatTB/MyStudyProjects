package com.github.patbattb.taskmanager.backend.manager.taskmanager;

import org.junit.jupiter.api.BeforeEach;

public final class DefaultTaskManagerTest extends TaskManagerTest<DefaultTaskManager> {

    @Override
    @BeforeEach
    void setUp() {
        super.setUp();
        taskManager = new DefaultTaskManager();
    }
}
