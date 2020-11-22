package com.openrules.cloud;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This is a client for different decision services deployed on cloud. Here is
 * an example of using this client to run an AWS Lambda function
 * "VacationDaysLambda".
 * 
 * 
 * <pre>
 * package com.vacation.days;
 * 
 * import vacation.days.lambda.Employee;
 * 
 * public class VacationDaysClient {
 * 
 *     public static void main(String[] args) throws Exception {
 * 
 *         String endpointUrl = "https://xxxxxxxx.execute-api.us-east-1.amazonaws.com/test/vacation-days";
 *         DecisionServiceClient client = new DecisionServiceClient(endpointUrl);
 *         Employee employee = new Employee();
 *         employee.setAge(50);
 *         employee.setService(20);
 *         employee.setId("Joe");
 *         client.putObject("employee", employee);
 * 
 *         if (client.execute()) {
 *             Employee updatedEmployee = client.getObject("employee", Employee.class);
 *             System.out.println("\nEmployee: " + updatedEmployee);
 *         }
 *     }
 * }
 * </pre>
 * 
 *
 */
public class DecisionServiceClient {

    static class Response {
        private int decisionStatusCode;
        private double rulesExecutionTimeMs;
        private String errorMessage;
        private JsonNode response;
        private JsonNode executedRules;

        public int getDecisionStatusCode() {
            return decisionStatusCode;
        }

        public void setDecisionStatusCode(int decisionStatusCode) {
            this.decisionStatusCode = decisionStatusCode;
        }

        public double getRulesExecutionTimeMs() {
            return rulesExecutionTimeMs;
        }

        public void setRulesExecutionTimeMs(double ruleExecutionTimeMs) {
            this.rulesExecutionTimeMs = ruleExecutionTimeMs;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public JsonNode getResponse() {
            return response;
        }

        public void setResponse(JsonNode response) {
            this.response = response;
        }

        public JsonNode getExecutedRules() {
            return executedRules;
        }

        public void setExecutedRules(JsonNode executedRules) {
            this.executedRules = executedRules;
        }

    }

    private ObjectMapper mapper = new ObjectMapper();

    private final URL endpoint;
    private Map<String, Object> request;
    private Response response;
    private boolean trace;

    public DecisionServiceClient(String endpoint) throws MalformedURLException {
        this.endpoint = new URL(endpoint);
        request = new HashMap<String, Object>();
        trace = true;
    }

    public void putObject(String key, Object object) {
        request.put(key, object);
    }

    public <T> T getObject(String key, Class<T> klass) throws JsonParseException, JsonMappingException, IOException {
        if (response == null)
            return null;
        JsonNode node = response.response.get(key);
        if (node == null) {
            return null;
        }
        return mapper.treeToValue(node, klass);
    }

    public boolean isTrace() {
        return trace;
    }

    public void setTrace(boolean trace) {
        this.trace = trace;
    }

    public boolean execute() throws Exception {
        if (trace) {
            request.put("trace",true);
        }
        else {
            request.remove("trace");
        }
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(request);
        if (trace) {
            System.out.println("DecisionServiceClient request JSON:\n" + json);
        }
        HttpURLConnection connection = (HttpURLConnection) this.endpoint.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json;utf-8");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);
        try (OutputStream os = connection.getOutputStream()) {
            os.write(json.getBytes("utf-8"));
        }

        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            System.err.println("Received error status " + responseCode);
            return false;
        }

        String resultJson = null;
        try (InputStream is = connection.getInputStream(); Scanner scanner = new Scanner(is, "utf-8")) {
            resultJson = scanner.useDelimiter("\\A").next();
            this.response = mapper.readValue(resultJson, Response.class);
            if (this.response.getDecisionStatusCode() != 200) {
                throw new Exception("Failed to execute DecisionServiceClient for " + endpoint 
                        + "\nError: " + this.response.getErrorMessage());
            }
            if (trace) {
                String prettyJson = ( this.response == null ? null : mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this.response));
                System.out.println("DecisionServiceClient response JSON:\n" + prettyJson);
            }
            return true;
        } catch (Exception e) {
            System.out.println("DecisionServiceClient invalid response:\n" + resultJson);
            throw new Exception("Failed to execute DecisionServiceClient for " + endpoint, e);
        }
    }
    
    public double getRulesExecutionTime() {
        return this.response.getRulesExecutionTimeMs();
    }
}
