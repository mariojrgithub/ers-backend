package endpoints;

import java.util.List;

import io.javalin.Javalin;
import pojo.EmployeePojo;
import pojo.RequestPojo;
import service.EmployeeService;
import service.EmployeeServiceImpl;
import service.RequestService;
import service.RequestServiceImpl;

public class ErsMain {

	public static void main(String[] args) {

		EmployeeService employeeService = new EmployeeServiceImpl();
		RequestService requestService = new RequestServiceImpl();

		// create and start a server
		Javalin myServer = Javalin.create(config -> config.enableCorsForAllOrigins()).start(4040);
		System.out.println("Service listening on port " + myServer.port());

		// fetch all employees
		myServer.get("/api/manager/all-employees", ctx -> {
			List<EmployeePojo> allEmployees = employeeService.fetchAllEmployees();
			ctx.json(allEmployees);
		});

		// fetch all requests
		myServer.get("/api/requests", ctx -> {
			List<RequestPojo> allRequests = requestService.fetchAllRequests();
			ctx.json(allRequests);
		});

		// fetch all pending requests
		myServer.get("/api/requests/pending", ctx -> {
			List<RequestPojo> allPendingRequests = requestService.fetchPendingRequests();
			ctx.json(allPendingRequests);
		});

		// fetch all resolved requests
		myServer.get("/api/requests/resolved", ctx -> {
			List<RequestPojo> allResolvedRequests = requestService.fetchResolvedRequests();
			ctx.json(allResolvedRequests);
		});

		// fetch requests by ID
		myServer.get("/api/requests/{id}", ctx -> {
			String id = ctx.pathParam("id");
			List<RequestPojo> rqById = requestService.fetchEmployeeRequests(Integer.parseInt(id));
			ctx.json(rqById);
		});

		// add a request
		myServer.post("/api/requests/add", ctx -> {
			RequestPojo newRequest = ctx.bodyAsClass(RequestPojo.class);
			RequestPojo returnedRequest = employeeService.createNewRequest(newRequest);

			ctx.json(returnedRequest);

		});
		
		// login employee
		myServer.get("/api/login", ctx -> {
			EmployeePojo login = ctx.bodyAsClass(EmployeePojo.class);
			EmployeePojo returnedLogin = employeeService.loginEmployee(login);
			
			ctx.json(returnedLogin);
		});
	}

}
