package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.demo.domain.Basket;
import com.example.demo.domain.Categories;
import com.example.demo.domain.Customer;
import com.example.demo.domain.Orders;
import com.example.demo.domain.Product;
import com.example.demo.domain.ProductImage;
import com.example.demo.domain.Product_option_info;
import com.example.demo.domain.Worker;

import com.example.demo.persistence.BasketRepository;
import com.example.demo.persistence.CategoriesRepository;
import com.example.demo.persistence.CustomerRepository;
import com.example.demo.persistence.OptionInfoRepo;
import com.example.demo.persistence.OrdersRepository;
import com.example.demo.persistence.ProductImageRepository;
import com.example.demo.persistence.ProductRepository;
import com.example.demo.persistence.WorkerRepository;


import org.junit.jupiter.api.BeforeEach;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"spring-servlet.xml"})
//
//@SpringBootTest(classes = {WorkerRepository.class, Worker.class}) 
//@SpringBootApplication(scanBasePackages={"com.example.persistence.WorkerRepository"})
////@RunWith(WorkerRepository.class)
////@ContextConfiguration(classes = {WorkerRepository.class, Worker.class}) 
//@EntityScan(basePackages = {"com.example.domain"})

@SpringBootTest
class QueryMethodTest {

	@Autowired	
	private WorkerRepository WorkerRepo;
	@Autowired
	private CustomerRepository CustomerRepo;
	@Autowired
	private ProductRepository ProductRepo;
	@Autowired
	private CategoriesRepository CategoRepo;
	@Autowired
	private BasketRepository basketRepo;
	@Autowired
	private ProductImageRepository ProImgRepo;

	@Autowired
	private OptionInfoRepo OptionRepository;
//	@Test
//	public void testInsertProduct() {
//        String str_cate[]= {"??????","??????","??????","?????????","?????????"};
//        Categories cate[]=new Categories[5];
//        for (int i = 0; i < 5; i++) {
//           cate[i]=new Categories();
//           cate[i].setCategoryName(str_cate[i]);
//         CategoRepo.save(cate[i]);
//        }
//     // ?????????
//     Product product = new Product();
//     product.setProductName("???????????????");
//     product.setPrice(3500);
//     product.setAllSale(false);
//     product.setCategories(cate[0]);
//     ProductRepo.save(product);
//
//     product = new Product();
//     product.setProductName("????????????");
//     product.setPrice(3000);
//     product.setAllSale(false);
//     product.setCategories(cate[0]);
//     ProductRepo.save(product);
//     
//     //??????
//     product = new Product();
//     product.setProductName("???????????????");
//     product.setPrice(1500);
//     product.setAllSale(false);
//     product.setCategories(cate[2]);
//     ProductRepo.save(product);
//     
//     product = new Product();
//     product.setProductName("????????????");
//     product.setPrice(2000);
//     product.setAllSale(false);
//     product.setCategories(cate[2]);
//     ProductRepo.save(product);
//     
//     //??????
//     product = new Product();
//     product.setProductName("??????");
//     product.setPrice(1200);
//     product.setAllSale(false);
//     product.setCategories(cate[1]);
//     ProductRepo.save(product);
//     
//     product = new Product();
//     product.setProductName("?????????");
//     product.setPrice(1200);
//     product.setAllSale(false);
//     product.setCategories(cate[1]);
//     ProductRepo.save(product);
//     
//     //?????????
//     product = new Product();
//     product.setProductName("????????????");
//     product.setPrice(1500);
//     product.setAllSale(false);
//     product.setCategories(cate[3]);
//     ProductRepo.save(product);
//     
//     product = new Product();
//     product.setProductName("????????????");
//     product.setPrice(1500);
//     product.setAllSale(false);
//     product.setCategories(cate[3]);
//     ProductRepo.save(product);
//     
//     //?????????
//     product = new Product();
//     product.setProductName("????????????????????????");
//     product.setPrice(700);
//     product.setAllSale(false);
//     product.setCategories(cate[4]);
//     ProductRepo.save(product);
//     
//     product = new Product();
//     product.setProductName("?????????");
//     product.setPrice(1000);
//     product.setAllSale(false);
//     product.setCategories(cate[4]);
//     ProductRepo.save(product);
//        }
	
//  @Test
//  public void testInsertCustomer() {
//	  Customer c=new Customer();
//      c.setUserId("super");
//      c.setUserPasswd("123");
//      c.setEmail(c.getUserId()+"@naver.com");
//      c.setCardCompany("???????????????");
//      c.setCardNum("333311000000");
//      c.setPhoneNum("010-0000-000");
//      c.setRole(1);
//      CustomerRepo.save(c);
//  }

//  @Test
//  public void testInsertCustomer() {
//     for(int i=0;i<5;i++) {
//        Customer c=new Customer();
//        c.setUserId("abcde"+i);
//        c.setUserPasswd("abcde"+i);
//        c.setEmail(c.getUserId()+"naver.com");
//        c.setCardCompany("???????????????");
//        c.setCardNum("333311000000"+i);
//        c.setPhoneNum("010-0000-000"+i);
//        CustomerRepo.save(c);
//     }
//  }

	
//	  @Test
//	  public void fktest() {
//		  System.out.println("++++++++++++++++++++++++++++");
//		  
//		  ProductRepo.findByProductName("???????????????").get(0).getInfo("Small", "None").getInfoid();
		  
		  
//		  System.out.println((ProductRepo.findByProductName("???????????????").get(0)).getInfo("Small", "None").getInfoid());
//		  ProductRepo.findByProductName("???????????????").get(0).getInfo("Small", "Hot").getSize()
//		  (Product_option_info)ProductRepo.findByProductName("???????????????").get(0).getInfo("Small", "Hot")
//		  	Basket basket=new Basket();
//		  	basket.setPCount(2);
//		  	basket.setProductinfo(null);
////		  	basket.setProduct(ProductRepo.findByProductName("???????????????").get(0));
//		  	basket.setCustomer(CustomerRepo.findByUserId("20173349").get(0));
//		  	basketRepo.save(basket);
//	  }
	  
//
//	@Test
//	public void inputimage() {
//		ProductImage pdi=new ProductImage();
//		ProductImage pdi2=new ProductImage();
//		pdi.setImageName("???????????????");
//		pdi.setImageLoad("/img/bulgogibuger.jpg");
//		pdi.setProduct(ProductRepo.findById(1).get());
//		ProImgRepo.save(pdi);
//		
//		pdi2.setImageName("????????????");
//		pdi2.setImageLoad("/img/shrimpbuger.jpg");
//		pdi2.setProduct(ProductRepo.findById(2).get());
//		ProImgRepo.save(pdi2);
//		
//	}
//}

	//????????? ??????
//	@Test
//	public void find() {
//		Categories cate =new Categories();
//		cate=CategoRepo.findById(1).get();
//		List<Product> productlist=ProductRepo.findByCategoryId(1);
//		for(Product p: productlist) {
//			System.out.println(p.toString());
//		}
//	}
	  
//	  @Test
//	  public void setadd() {
//
//
//	     // ?????????
//	     Product product = new Product();
//	     product.setProductName("1955?????? ??????");
//	     product.setPrice(0);
//	     product.setAllSale(false);
//	     product.setCategories(CategoRepo.findById(6).get());
//	     ProductRepo.save(product);
//
//
//
//
//	        }
}

