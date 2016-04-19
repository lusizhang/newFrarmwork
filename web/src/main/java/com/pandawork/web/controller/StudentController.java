package com.pandawork.web.controller;

import com.pandawork.common.entity.Student;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.web.spring.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * StudentController
 *
 * @author: mayuan
 * @time: 2015/8/26 16:57
 */
@Controller
@RequestMapping(value = "")
@SessionAttributes("studentNumber")
public class StudentController extends AbstractController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String tologin() {
        return "/login";
    }

    @RequestMapping(value = "form",method=RequestMethod.POST)
    public String toform(Model model,@RequestParam("stuNum")Integer stuNum,
                                     @RequestParam("pwd")String pwd)
    {
        try {
            if(studentService.checkLogin(stuNum,pwd)){
                model.addAttribute("student",studentService.queryByStudentNumber(stuNum));
                return "form";
            }else{
                return "login";
            }
        } catch (SSException ee) {
            LogClerk.errLog.error(ee);
            sendErrMsg(ee.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
    }

    @RequestMapping(value = "edit/${studentNumber}" ,method = RequestMethod.GET)
    public String toedit(Model model,@PathVariable("studentnumber")Integer studentnumber ){
        model.addAttribute("studentnumber",studentnumber);
        return "edit";
    }

    @RequestMapping(value="update/{studentNumber}",method = RequestMethod.POST)
    public String toupdate(Model model, Student student,
                         @RequestParam("newName")String newName,
                         @RequestParam("newSex")String newSex,
                         @RequestParam("newGrade")Integer newGrade,
                         @RequestParam("newClassNumber")Integer newClassNumber,
                         @RequestParam("newCollege")Integer newCollege,
                         @RequestParam("newIsGoodStudent")String newIsGoodStudent,
                         @RequestParam("newPassword")String newPassword,
                         @RequestParam("stuNum")Integer stuNum){
        try {
            if(studentService.checkStudentNumber(stuNum)){
           // model.addAttribute("student",studentService.queryByStudentNumber(stuNum));
            student.setStudentName(newName);
            student.setSex(newSex);
            student.setGrade(newGrade);
            student.setClassNumber(newClassNumber);
            student.setCollege(newCollege);
            student.setIsGoodStudent(newIsGoodStudent);
            student.setPassword(newPassword);
            studentService.update(student);
            return "redirect:/form";
            } else {
                return "edit";
            }
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
    }
}


