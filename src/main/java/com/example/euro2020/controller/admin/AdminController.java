package com.example.euro2020.controller.admin;

import com.example.euro2020.controller.MainControllers;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
@PreAuthorize("@securityService.hasRole('ADMIN')")
public class AdminController extends MainControllers {

}
