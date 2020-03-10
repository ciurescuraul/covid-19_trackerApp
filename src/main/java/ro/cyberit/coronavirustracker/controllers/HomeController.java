package ro.cyberit.coronavirustracker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ro.cyberit.coronavirustracker.models.LocationStats;
import ro.cyberit.coronavirustracker.services.CoronaVirusDataService;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/")
    public String home(Model model) {
        List<LocationStats> allStats = coronaVirusDataService.getAllStats();
        model.addAttribute("locationStats", allStats);
        int totalCases = allStats.stream().mapToInt(stat->stat.getLatestTotalCases()).sum();
        int totalNewCases = allStats.stream().mapToInt(stat->stat.getDiffFromPrevDay()).sum();
        model.addAttribute("totalNewCases", totalNewCases);
        model.addAttribute("totalReportedCases", totalCases);

        // Cases From Romania today
        int romaniaTotalCases = allStats.stream().filter(locationStats -> locationStats.getCountry().equals("Romania")).mapToInt(stat->stat.getLatestTotalCases()).sum();
        model.addAttribute("romaniaTotalReportedCases", romaniaTotalCases);

        return "home";
    }
}
