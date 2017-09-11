package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view
        Job theJob=jobData.findById(id);
        model.addAttribute("jobs",theJob);

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        if (errors.hasErrors()){
            model.addAttribute("name", errors);
            return"new-job";
        }


        Employer anEmployer = jobData.getEmployers().findById(jobForm.getEmployerId());
        Location alocation = jobData.getLocations().findById(jobForm.getLocationId());
        CoreCompetency aCompetency = jobData.getCoreCompetencies().findById(jobForm.getCoreCompetencyId());
        PositionType aPositionType = jobData.getPositionTypes().findById(jobForm.getPositionTypeId());
        String aName = jobForm.getName();

        Job newJob=new Job(aName, anEmployer, alocation, aPositionType, aCompetency);
        jobData.add(newJob);
        int aId = newJob.getId();


        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.

        return "redirect:/job?id="+ aId;

    }
}
