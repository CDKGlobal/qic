package com.testing123.vaadin;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class getSonarMetrics {
	
	private static final String LINK = "http://sonar.cobalt.com/api/metrics";
	
	private static Set<String> metricsSet;
	
	
	public getSonarMetrics(){
		metricsSet = new HashSet<String>();
		try{
			URL link = new URL(LINK).toURI().toURL();
			ObjectMapper mapper = new ObjectMapper();
			List<WebData> fileList = mapper.readValue(link, new TypeReference<List<WebData>>() {});
			for (WebData file : fileList) {
				metricsSet.add(file.getKey());
			}
			System.out.println(metricsSet.toString());
			//String[] ARRAY_OF_METRICS = new String[] { "lines","generated_lines","ncloc","generated_ncloc","classes","files","directories","packages","functions","accessors","statements","public_api","projects","comment_lines","comment_lines_density","comment_blank_lines","public_documented_api_density","public_undocumented_api","commented_out_code_lines","complexity","class_complexity","function_complexity","file_complexity","class_complexity_distribution","function_complexity_distribution","file_complexity_distribution","tests","test_execution_time","test_errors","skipped_tests","test_failures","test_success_density","test_data","coverage","new_coverage","lines_to_cover","new_lines_to_cover","uncovered_lines","new_uncovered_lines","line_coverage","new_line_coverage","coverage_line_hits_data","conditions_to_cover","new_conditions_to_cover","uncovered_conditions","new_uncovered_conditions","branch_coverage","new_branch_coverage","branch_coverage_hits_data","conditions_by_line","covered_conditions_by_line","it_coverage","new_it_coverage","it_lines_to_cover","new_it_lines_to_cover","it_uncovered_lines","new_it_uncovered_lines","it_line_coverage","new_it_line_coverage","it_coverage_line_hits_data","it_conditions_to_cover","new_it_conditions_to_cover","it_uncovered_conditions","new_it_uncovered_conditions","it_branch_coverage","new_it_branch_coverage","it_conditions_by_line","it_covered_conditions_by_line","duplicated_lines","duplicated_blocks","duplicated_files","duplicated_lines_density","duplications_data","weighted_violations","violations_density","violations","blocker_violations","critical_violations","major_violations","minor_violations","info_violations","new_violations","new_blocker_violations","new_critical_violations","new_major_violations","new_minor_violations","new_info_violations","abstractness","instability","distance","dit","noc","rfc","rfc_distribution","lcom4","lcom4_blocks","lcom4_distribution","suspect_lcom4_density","ca","ce","dsm","package_cycles","package_tangle_index","package_tangles","package_feedback_edges","package_edges_weight","file_cycles","file_tangle_index","file_tangles","file_feedback_edges","file_edges_weight","authors_by_line","revisions_by_line","last_commit_datetimes_by_line","unreviewed_violations","new_unreviewed_violations","false_positive_reviews","active_reviews","unassigned_reviews","unplanned_reviews","ncloc_data","comment_lines_data","alert_status","profile","profile_version","burned_budget","business_value","team_size","issues","sqale_index","sqale_rating","sqale_rating_file_distribution","sqale_effort_to_grade_a","sqale_effort_to_grade_b","sqale_effort_to_grade_c","sqale_effort_to_grade_d","blocker_remediation_cost","critical_remediation_cost","major_remediation_cost","minor_remediation_cost","owaspviolations","owaspvulnerabilities","owaspactiverules","owasptotalrules","owaspprofilerules","owasptop10rules","owaspfactorrisk","owasprulesdata","overall_coverage","new_overall_coverage","overall_lines_to_cover","new_overall_lines_to_cover","overall_uncovered_lines","new_overall_uncovered_lines","overall_line_coverage","new_overall_line_coverage","overall_coverage_line_hits_data","overall_conditions_to_cover","new_overall_conditions_to_cover","overall_uncovered_conditions","new_overall_uncovered_conditions","overall_branch_coverage","new_overall_branch_coverage","overall_conditions_by_line","overall_covered_conditions_by_line","scm.hash","days_since_last_commit","complexity_in_classes","complexity_in_functions","false_positive_issues","open_issues","reopened_issues","confirmed_issues","new_technical_debt","projects_in_error","projects_in_warning","development_cost","info_remediation_cost","delta-coverage","delta-line-coverage","delta-branch-coverage","delta-uncovered-lines","delta-uncovered-conditions","delta-lines","delta-conditions"};
			//Set<String> SET_OF_METRICS = new HashSet<String>(Arrays.asList(ARRAY_OF_METRICS));
			//System.out.println(SET_OF_METRICS.toString());
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean sonarHasMetric(String metric){
		return metricsSet.contains(metric);
	}
	
	public static boolean sonarHasMetrics(String metric1, String metric2){
		return metricsSet.contains(metric1) && metricsSet.contains(metric2);
	}
}
