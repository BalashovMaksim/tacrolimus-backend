package com.tacrolimus.backend.controller;

import com.tacrolimus.backend.dto.CreateDto;
import com.tacrolimus.backend.dto.PersonReadDto;
import com.tacrolimus.backend.enu.OrganEnum;
import com.tacrolimus.backend.service.PersonService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PersonControllerTest {
}
