package com.example.project.controller;

import com.example.project.dto.MyWorkPageDTO;
import com.example.project.dto.TaskCardDTO;
import com.example.project.dto.UserSummaryDTO;
import com.example.project.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Test
    @WithMockUser(username = "neha@example.com")
    void getMyWork_shouldReturnGroupedTasks() throws Exception {
        MyWorkPageDTO data = new MyWorkPageDTO();
        TaskCardDTO todo = new TaskCardDTO();
        todo.setId(102L);
        todo.setTitle("Login");
        todo.setPriority("HIGH");
        todo.setStatus("TODO");
        todo.setAssignee(new UserSummaryDTO(15L, "Neha"));
        data.setTodo(List.of(todo));
        data.setInProgress(List.of());
        data.setDone(List.of());

        when(taskService.getMyWorkTasks(eq(15L), any(), any(), any(), any(), eq(0), eq(20)))
                .thenReturn(data);

        mockMvc.perform(get("/api/v1/tasks/my-work")
                        .param("page", "0")
                        .param("size", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Tasks fetched successfully"))
                .andExpect(jsonPath("$.data.todo[0].title").value("Login"))
                .andExpect(jsonPath("$.data.todo[0].status").value("TODO"));
    }
}
