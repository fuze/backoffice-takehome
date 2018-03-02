package com.fuze.takehome.tests;

import org.junit.Assert;
import org.junit.BeforeClass;

import java.util.List;
import org.junit.Test;

import com.fuze.takehome.model.Company;
import com.fuze.takehome.model.Department;

public class OrgTreeValidatorTest {

	private OrgTreeValidator orgTreeValidator;
	
	private Company company;
	
	private Department department1;
	private Department department2;
	
	private User user1;
	private User user2;
	
	@BeforeClass
	public void setup() {
		orgTreeValidator = new OrgTreeValidator();
		
		company = CompanyGenerator.getRandomCompany();
		
		department1 = DepartmentGenerator.getRandomDepartment(company);
		department2 = DepartmentGenerator.getRandomDepartment(company);
		
		user1 = UserGenerator.getRandomUser(company, department1);
		user2 = UserGenerator.getRandomUser(company, department2);
	}
		
	
//	@Test
//	public void testOrgChartValid() {
//		//For these random Users identify the distinct companies and then create a valid org chart for each
//		List<User> userList = UserGenerator.getRandomListOfUsers(3000);
//		
//		TreeNode<OrgTreeItem> orgTree;
//		
//		
//		
//		Assert.fail("Not yet implemented");
//	}
	
	@Test
	/*
	 * Valid Tree
	 *                   company
	 *                   /    \
	 *           department1 department2
	 *               |           |
	 *             user1       user2
	 * 
	 */
	public void testValidTree(){
		//Good tree structure
		TreeNode<OrgTreeItem> companyNode = TreeNode.from(company);
		
		companyNode
			.addChild(department1)
			.addChild(user1);
		
		companyNode
			.addChild(department2)
			.addChild(user2);
				
		System.out.println(String.format("----Starting test of %s", companyNode.getData().getName()));
		Assert.assertTrue("Not a valid orgTree",orgTreeValidator.validate(companyNode,OrgTreeType.COMPANY));
	}
	

	@Test
	/*
	 * Invalid Tree
	 *                   company
	 *                   /     \
	 *           department1 department2
	 *              |            |
	 *            user1   nestedDepartment
	 * 
	 */        
	public void testInvalidTreeNestedDepartment(){
		
		Department nestedDepartment = DepartmentGenerator.getRandomDepartment(company);
		
		//Bad tree structure
		TreeNode<OrgTreeItem> companyNode = TreeNode.from(company);
		
		companyNode
			.addChild(department1)
			.addChild(user1);
		
		companyNode
			.addChild(department2)
			.addChild(nestedDepartment);
		
		System.out.println(String.format("----Starting test of %s", companyNode.getData().getName()));
		Assert.assertFalse("This is a valid tree, expected a failure",orgTreeValidator.validate(companyNode,OrgTreeType.COMPANY));
	}

	@Test
	/*
	 * Invalid Tree
	 *                   company
	 *                  /      \
	 *          department1 department2
	 *              |           |
	 *            user1    nestedCompany
	 * 
	 */     
	public void testInvalidTreeNestedCustomer(){
		Company nestedCompany = CompanyGenerator.getRandomCompany();
		
		//Bad tree structure
		TreeNode<OrgTreeItem> companyNode = TreeNode.from(company);
		
		companyNode
			.addChild(department1)
			.addChild(user1);
		
		companyNode
			.addChild(department2)
			.addChild(nestedCompany);
			
		System.out.println(String.format("----Starting test of %s", companyNode.getData().getName()));
		Assert.assertFalse("This is a valid tree, expected a failure",orgTreeValidator.validate(companyNode,OrgTreeType.COMPANY));
	}

	@Test
	/*
	 * Invalid Tree
	 *                  company
	 *                     |
	 *               nestedCompany
	 * 
	 */     
	public void testInvalidTreeNestedCustomer2(){
		Company nestedCompany = CompanyGenerator.getRandomCompany();
		
		//Bad tree structure
		TreeNode<OrgTreeItem> companyNode = TreeNode.from(company);
		
		companyNode.addChild(nestedCompany);
		
		
		System.out.println(String.format("----Starting test of %s", companyNode.getData().getName()));
		Assert.assertFalse("This is a valid tree, expected a failure",orgTreeValidator.validate(companyNode,OrgTreeType.COMPANY));
	}
	
	@Test
	/*
	 * Invalid Tree
	 *                   company
	 *                   /     \
	 *          department1  department2
	 *               |            |
	 *             user1        user2
	 *                            |
	 *                        nestedUser
	 * 
	 */
	public void testInvalidTreeNestedUser(){
		User nestedUser = UserGenerator.getRandomUser();
		
		//Bad tree structure
		TreeNode<OrgTreeItem> companyNode = TreeNode.from(company);
		
		companyNode
			.addChild(department1)
			.addChild(user1);
		
		companyNode
			.addChild(department2)
			.addChild(user2)
			.addChild(nestedUser);
				
		System.out.println(String.format("----Starting test of %s", companyNode.getData().getName()));
		Assert.assertFalse("This is a valid tree, expected a failure",orgTreeValidator.validate(companyNode,OrgTreeType.COMPANY));
	}
	
	@Test
	/*
	 * Invalid Tree
	 *                   company
	 *                   /     \
	 *          department1  department2
	 *               |            |
	 *             user1        user2
	 *               |
	 *          nestedUser
	 * 
	 */
	public void testInvalidTreeNestedUser2(){
		User nestedUser = UserGenerator.getRandomUser();
		
		//Bad tree structure
		TreeNode<OrgTreeItem> companyNode = TreeNode.from(company);
		
		companyNode
			.addChild(department1)
			.addChild(user1)
			.addChild(nestedUser);
		
		companyNode
			.addChild(department2)
			.addChild(user2);
						
		System.out.println(String.format("----Starting test of %s", companyNode.getData().getName()));
		Assert.assertFalse("This is a valid tree, expected a failure",orgTreeValidator.validate(companyNode,OrgTreeType.COMPANY));
	}
	
	@Test
	/*
	 * Invalid Tree
	 *                   company
	 *                      |	
	 *                 department1
	 *                      |
	 *                    user1
	 *                      |
	 *                 nestedUser
	 *                      |
	 *              nestedNestedUser
	 */
	public void testInvalidTreeNestedUser3(){
		User nestedUser = UserGenerator.getRandomUser();
		User nestedNestedUser = UserGenerator.getRandomUser();
		
		//Bad tree structure
		TreeNode<OrgTreeItem> companyNode = TreeNode.from(company);
		
		companyNode
			.addChild(department1)
			.addChild(user1)
			.addChild(nestedUser)
			.addChild(nestedNestedUser);
						
		System.out.println(String.format("----Starting test of %s", companyNode.getData().getName()));
		Assert.assertFalse("This is a valid tree, expected a failure",orgTreeValidator.validate(companyNode,OrgTreeType.COMPANY));
	}
}
