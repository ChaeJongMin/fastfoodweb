package com.example.demo.Controller;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.filechooser.FileSystemView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.domain.Basket;
import com.example.demo.domain.Categories;
import com.example.demo.domain.Customer;
import com.example.demo.domain.Orders;
import com.example.demo.domain.Product;
import com.example.demo.domain.ProductImage;
import com.example.demo.domain.Product_option_info;
import com.example.demo.domain.Size;
import com.example.demo.domain.Temperature;
import com.example.demo.domain.Worker;

import com.example.demo.persistence.BasketRepository;
import com.example.demo.persistence.CategoriesRepository;
import com.example.demo.persistence.CustomerRepository;
import com.example.demo.persistence.OptionInfoRepo;
import com.example.demo.persistence.OrdersRepository;
import com.example.demo.persistence.ProductImageRepository;
import com.example.demo.persistence.ProductRepository;
import com.example.demo.persistence.SizeRepository;
import com.example.demo.persistence.TemperRepository;
import com.example.demo.persistence.WorkerRepository;

import lombok.Getter;

@Controller // This means that this class is a Controller
@RequestMapping(path = "/fastfood") // This means URL's start with /demo (after Application path)
public class MainController {
	@Autowired 	// This means to get the bean called userRepository
	private BasketRepository BasketRepo;
	@Autowired
	private CategoriesRepository CategoriRepo;
	@Autowired
	private CustomerRepository CustomerRepo;
	@Autowired
	private OrdersRepository OrdersRepo;
	@Autowired
	private ProductRepository ProductRepo;
	@Autowired
	private WorkerRepository WorkerRepository;
	@Autowired
	private ProductImageRepository ProductImgRepo;

	@Autowired
	private SizeRepository SizeRepository;
	@Autowired
	private OptionInfoRepo OptionRepository;
	@Autowired
	private TemperRepository TemperRepo;

	static String s3Url="https://fastfood-spring-build.s3.ap-northeast-2.amazonaws.com/img/";
	@GetMapping("/logout")
    public String logoutGet(HttpSession session, HttpServletRequest request) {
       session = request.getSession();
       session.invalidate();
       System.out.println("*******?????? ?????????*******");
       System.out.println("*******????????????*******");
       return "fastfood/login";
    }
	
	 @GetMapping("/CustomerUpdate")
     public String cUpdate(Model model,Customer customer) {
        //Iterable<>
        return "fastfood/CustomerUpdate";
     }
     @PostMapping("/CustomerUpdate")
     public String cUpdateP(HttpSession session,Customer customer, @RequestParam("id") int id) {
        Customer c=CustomerRepo.findById(id).get();
        c.setPhoneNum(customer.getPhoneNum());
        c.setCardCompany(customer.getCardCompany());
        c.setCardNum(customer.getCardNum());
        c.setEmail(customer.getEmail());
        c.setUserId(customer.getUserId());
        c.setUserPasswd(customer.getUserPasswd());
        CustomerRepo.save(c);
        session.setAttribute("user", c);
        return "forward:fastfood/menu";
     }
	
	@GetMapping("/register")
	public String signupView() {
		return "fastfood/register";
	}
	
	@PostMapping("/register")
	public String signupSuccessView(Customer user) {
		CustomerRepo.save(user);
		return "redirect:fastfood/login";
	}
	
	@GetMapping("/login")
	public String loginView( ) {
		return "fastfood/login";
	}
	
	@PostMapping("/login")
	public String loginSuccessView(Model model, String userId, String userPasswd,HttpSession session) {
		System.out.println("login controller");
		System.out.println(userId + ", " + userPasswd);
		Iterable<Customer> cusList = CustomerRepo.findAll();
		int isloginSuccess=0;
		for (Customer c : cusList) {			
			if(userId.equals(c.getUserId()) && userPasswd.equals(c.getUserPasswd())) {
				isloginSuccess=1;
				System.out.println("????????? ?????????"+isloginSuccess);
				if(c.getRole()==1) {
					session.setAttribute("user", c);
					return "forward:/fastfood/superhome";
					}
				else {
					session.setAttribute("user", c);
					return "forward:/fastfood/menu";
					}
			}
		}
		
		model.addAttribute("isloginSuccess", isloginSuccess);
		System.out.println(isloginSuccess);
		
		return "fastfood/login";
	}
	@GetMapping("/new_food")
	public String new_foodView() {		
					
		return "fastfood/new_food";
	}
	
	@PostMapping("/new_food")
	public String new_foodpage(String foodName,Integer foodPrice,String foodCate) {
		
		System.out.println(foodName);
		System.out.println(foodPrice);
		System.out.println(foodCate);
		
		Categories c =CategoriRepo.findByCategoryName(foodCate).get(0);
		System.out.println(c.getCategoryId());
		
		return "fastfood/new_food";
	}
	
	@GetMapping("/fileupload")
	public String fileupload() {		
					
		return "fastfood/file_upload";
	}

	@PostMapping("/fileupload")
	public String uploadSingle(@RequestParam("files") MultipartFile file) throws Exception {
	    String rootPath = FileSystemView.getFileSystemView().getHomeDirectory().toString();
	    System.out.println(rootPath);
	    String basePath = rootPath + "\\" + "media";
	    System.out.println(basePath);
	    String filePath = "C:\\Users\\home\\Documents\\workspace-spring-tool-suite-4-4.11.1.RELEASE\\fastfood_up\\src\\main\\resources\\static\\img" + "\\" + file.getOriginalFilename();
	    File dest = new File(filePath);
	    file.transferTo(dest); // ?????? ????????? ?????? ??????
	    System.out.println(filePath);
	    return "fastfood/file_upload";
	}
	
	@GetMapping("/menu")
	public String homepageView() {		
		System.out.println("@@GetMapping ?????? ?????????  called...");				
		return "fastfood/menu";
	}
	
	@PostMapping("/menu")
	public String homepage( HttpSession session) {
		System.out.println(" @@PostMapping ?????? ????????? called...");		
		System.out.println("**************************************");			
		return "fastfood/menu";
	}

	 @GetMapping(value="/Hdetailmenu")
	    public String detailmenuView(Model model,@RequestParam("menuid") int menuid,@RequestParam("menuname") String menuname,HttpSession session){      
	      System.out.println(" @@GetMapping ???????????? ????????? called...");   
//	      List<ProductImage> ProImgList=new ArrayList<ProductImage>();
	      Categories cate = CategoriRepo.findById(menuid).get();
	         for (Product product : cate.getProductList()) {
	            System.out.println("---> " + product.getProductName());
	            System.out.println(product.getPId());
	            for(Product_option_info option : product.getInfoList()) {
	               System.out.print(option.getSize().getSizename()+", ");
	               System.out.println(option.getTemperature().getTempname());
	            }
	            System.out.println();
	           
	         }
	         System.out.println("????????? ?????? ??????"+menuid);   
	 
	      List<Product> productList=new ArrayList<Product>();
	      for(Product product : ProductRepo.findAll()) {
	         if(product.getCategories().getCategoryId()==menuid) {
	            productList.add(product);
	            System.out.println("---> " + product.getProductName());
	            System.out.println(product.getPId());
	            
	         }
	      }
	      if (menuid == 6) {
	          if(menuid==6) {
	              List<Categories> CatedrinkList=new ArrayList<Categories>();
	              List<Categories> CatesideList=new ArrayList<Categories>();
	              List<Product> drinkList=new ArrayList<Product>();            
	              List<Product> sideList=new ArrayList<Product>();
	              List<Integer> dpList=new ArrayList<Integer>();
	              List<Integer> spList=new ArrayList<Integer>();
	              CatedrinkList=CategoriRepo.findByCategoryName("??????");
	              CatesideList=CategoriRepo.findbyDrinkMenu("?????????", "?????????");
	              for(Categories c1:CatedrinkList ) { 
	                for(Product p: c1.getProductList()) {
	                   drinkList.add(p);
	                   if(p.getPrice()<=1500) {
	                      dpList.add(0);
	                   }
	                   else {
	                      
	                      dpList.add(p.getPrice()-1200);   
	                   }
	                }
	              }
	              for(Categories c2:CatesideList ) { 
	                 for(Product p: c2.getProductList()) {
	                    sideList.add(p);
	                    if(p.getPrice()<=1500) {
	                      spList.add(0);
	                   }
	                   else {
	                      
	                      spList.add(p.getPrice()-1500);   
	                   }
	                 }
	               }
	              model.addAttribute("sideList",sideList);
	              model.addAttribute("spList",spList);
	              model.addAttribute("drinkList",drinkList);
	              model.addAttribute("dpList",dpList);
	           }
	     }
	       
	      model.addAttribute("productlist",cate.getProductList());
	      model.addAttribute("goodslist",OptionRepository.findAll());
	      model.addAttribute("menuid",menuid);      
	      model.addAttribute("menuname",menuname);
		  model.addAttribute("s3url",s3Url);
	      return "fastfood/Hdetailmenu";
	    }


	@PostMapping(value="/Hdetailmenu")
    public String Hdetailmenu(@RequestParam("mid") int mid,Model model,HttpSession session,@RequestParam("pname") String pname,
          @RequestParam("size") String size,@RequestParam("temp") String temp){
      
      model.addAttribute("menuid",mid);
      System.out.println(" @@PostMapping homepageView called...");
      System.out.println("**********@PostMapping(\"/Hdetailmenu\")****************************");

      System.out.println("?????? ?????? ??????: " + mid);      
      System.out.println("????????? ??????: "+pname);
      System.out.println("??????: "+size);
      System.out.println("??????: "+temp);
      //int menuid=Integer.parseInt(mid);
      //????????? ????????? ?????? ??? ????????? option ????????? ???????????? ????????? ????????????
      if(mid!=6) {
         Product save_p=ProductRepo.findByProductName(pname).get(0);
         Size save_s=(SizeRepository.findBySizename(size).get(0));      
         Temperature save_t=TemperRepo.findByTempname(temp).get(0);
         
         Product_option_info save_o=OptionRepository.findOptionbyinfos(save_p, save_s, save_t).get(0);

         System.out.println("???????????????: " +   save_o.getInfoid());
         System.out.println("??????: " +   save_o.getSize().getSizename());
         System.out.println("??????: " +   save_o.getTemperature().getTempname());
         
         Basket basket = new Basket();
         basket.setProductinfo(save_o);
         basket.setCustomer((Customer)session.getAttribute("user"));
         basket.setPCount(1);
         basket.setInfo(String.valueOf(save_o.getInfoid()));
         basket.setPrice(save_o.getPrice());
         BasketRepo.save(basket);
      }
      
      else {
    	  Product_option_info[] save_o=new Product_option_info[3];
    	  Product save_p[]=new Product[3];
    	  String[] setpanme=pname.split(",");
    	  int price= Integer.parseInt(setpanme[3]);
    	  
    	  for(int i=0;i<3;i++) {
    		  
    		  save_p[i]=ProductRepo.findByProductName(setpanme[i]).get(0);
    		  Size save_s=(SizeRepository.findBySizename(size).get(0));      
              Temperature save_t=TemperRepo.findByTempname(temp).get(0);
 
              save_o[i]=OptionRepository.findOptionbyinfos(save_p[i], save_s, save_t).get(0);
              System.out.println("???????????????: " +   save_o[i].getInfoid());
              System.out.println("??????: " +   save_o[i].getSize().getSizename());
              System.out.println("??????: " +   save_o[i].getTemperature().getTempname());
    	  }
    	  
    	  String info=save_o[0].getInfoid()+","+save_o[1].getInfoid()+","+save_o[2].getInfoid();
          
          Basket basket = new Basket();
          basket.setProductinfo(save_o[0]);
          basket.setCustomer((Customer)session.getAttribute("user"));
          basket.setPCount(1);
          basket.setInfo(info);
          basket.setPrice(price);
          BasketRepo.save(basket);

      }

    return null;
   }
	
	
	@GetMapping("/mybasket")
    public String cartView(Model model,HttpSession session){      
		Iterable<Basket> basketList = BasketRepo.findByCustomer((Customer)session.getAttribute("user"));
		for(Basket b:basketList) {
			System.out.println(b);
		}
		//	Iterable<Basket> basketList = BasketRepo.find();
		model.addAttribute("s3url",s3Url);
      return "fastfood/my_baket";
    }
	
	@PostMapping("/mybasket")
    public String cartPost(Model model,HttpSession session){ 
		System.out.println(" @@PostMapping ShoppingCarteView called...");
	      System.out.println("**********@PostMapping(\"/ShoppingCart\")****************************");
		List<Basket> basketList = BasketRepo.findByCustomer((Customer)session.getAttribute("user"));
		ArrayList<Product> productList =new ArrayList<Product>();
		ArrayList<Product_option_info> productInfoList =new ArrayList<Product_option_info>();
		ArrayList<Integer> priceList =new ArrayList<Integer>();			
		int productcount=0;
		
		//???????????? ????????? ?????? ????????????
		for(int i=0;i<basketList.size(); i++) {
			
			for(int j=i+1; j<basketList.size(); j++) {	
				
				if(basketList.get(i).getInfo().equals(basketList.get(j).getInfo())) { //???????????? ?????? ???
					if(basketList.get(i).getCustomer().getId()==basketList.get(i).getCustomer().getId()){
						basketList.get(i).setPCount(basketList.get(i).getPCount()+1); //?????? ??????
						BasketRepo.save(basketList.get(i)); // ?????? ??????
						BasketRepo.deleteById(basketList.get(j).getBid()); //????????? ????????? ??????????????? ??????
						basketList.remove(j); //????????? ????????? ??????????????? ??????
					}
										
				}
			}
		}
		
		//?????? ?????????, ?????? ????????? ??????
		for(Basket b:basketList) {
			productInfoList.add(OptionRepository.findById(b.getProductinfo().getInfoid()).get());
			priceList.add(OptionRepository.findById(b.getProductinfo().getInfoid()).get().getPrice()*b.getPCount());
			
			productcount += b.getPCount();
		}
		int totalprice=0;
		for(int i=0; i<priceList.size(); i++) {
			totalprice+=priceList.get(i);
		}
		
		Map<Integer, String[] >map=new HashMap<>();
		
		for(Basket b:basketList) {
			
			if(b.getProductinfo().getProduct().getCategories().getCategoryName().equals("??????")) {
				
			String[] setpanme=b.getInfo().split(",");
			
			String[] sidemenu= {OptionRepository.findById(Integer.parseInt(setpanme[1])).get().getProduct().getProductName()
								,OptionRepository.findById(Integer.parseInt(setpanme[2])).get().getProduct().getProductName()};
			map.put(b.getBid() , sidemenu);
			}
			
			productInfoList.add(OptionRepository.findById(b.getProductinfo().getInfoid()).get());
			priceList.add(OptionRepository.findById(b.getProductinfo().getInfoid()).get().getPrice()*b.getPCount());
			
			
		}
		model.addAttribute("basketMap",map);
		model.addAttribute("productInfoList",productInfoList);
		model.addAttribute("basketList",basketList); // html each??? ????????????
		model.addAttribute("priceList",priceList); 
		model.addAttribute("productList",productList);
		model.addAttribute("totalprice",totalprice);
		model.addAttribute("productcount",productcount);
		model.addAttribute("s3url",s3Url);
      return "fastfood/my_baket";
    }
	   
	@ResponseBody
	@PostMapping("/basket_save")
	public String basket_save(@RequestParam(value = "basketarray[]") List<String> basketarray, HttpSession session,
			@RequestParam(value = "counts[]") List<String> counts, @RequestParam("checkpage") int checkpage)
			throws Exception {
		System.out.println(" @@PostMapping basket_saveView called...");
		ArrayList<Basket> basketList = new ArrayList<Basket>();
		Iterable<Basket> deleteList = BasketRepo.findByCustomer((Customer) session.getAttribute("user"));
		
		System.out.println("************????????? ?????? ?????? ??????**************");
		for (String b : basketarray) { //????????? ?????? ?????? ??????
			basketList.add(BasketRepo.findById(Integer.parseInt(b)).get());
			System.out.println(b);
		}
		for (Basket b : deleteList) { //?????? ????????? ???????????? ????????? ?????? ??????
			BasketRepo.delete(b);
		}	
		
		
		for (int i = 0; i < basketList.size(); i++) { //?????? ????????? ?????? ?????? ????????? ??????
			Basket newbasket = new Basket();
			newbasket = basketList.get(i);
			newbasket.setPCount(Integer.parseInt((counts.get(i))));
			System.out.println("????????? ?????????: " + newbasket.getBid() + " ????????? ??????: " + newbasket.getPCount());
			BasketRepo.save(newbasket);
		}
		String message="AJAX ??????";
		return message;
	}
	
	
	 @GetMapping("/Payment")
	   public String paymentView(HttpSession session,Model model) {
	      System.out.println(" @@GetMapping ??????????????? called...");
	      Iterable<Basket> basketList = BasketRepo.findByCustomer((Customer) session.getAttribute("user"));
	      
	      int total=calcu_price(basketList);
	      System.out.println("%%%%%%%%%%%%%"+total+"%%%%%%");
	      model.addAttribute("id",((Customer)session.getAttribute("user")).getUserId());
	      
	      model.addAttribute("totalprice", total);
	      return "fastfood/Payment";
	            
	   }
	   @PostMapping("/Payment")
	   public String payment(HttpSession session,Model model,String cardCompany, String cardNumber) {
	      System.out.println(" @@PostMapping ??????????????? called...");
	      System.out.println("????????? ???: "+cardCompany+cardNumber);
	      Iterable<Basket> basketList = BasketRepo.findByCustomer((Customer) session.getAttribute("user"));
	      Customer c=(Customer)session.getAttribute("user");
	      int total=calcu_price(basketList);
	      model.addAttribute("id",c.getUserId());
	      model.addAttribute("totalprice", total);
	      int isCheck=0;
	            
	      if(!cardNumber.equals("")&&!cardCompany.equals("")) {//?????? X         
	         int checkResult=checkUserPayInfo(cardNumber,cardCompany,
	               (Customer)session.getAttribute("user")); //????????????
	         switch(checkResult) {
	         case 1:
	            isCheck=1;
	            break;
	         case -1:
	            isCheck=-1;
	            break;
	         case -2:
	            isCheck=-2;
	            break;
	         }
	         model.addAttribute("check",isCheck);   
	      }
	      else { //?????? O
	        isCheck=-3;   
	        model.addAttribute("check",isCheck);   
	      }
	         
	      if(isCheck==1) {               
	         for(Basket b:basketList) {
	            Orders o=new Orders();
	            o.setCustomer(c);
	            
	            
	            o.setProduct(b.getProductinfo().getProduct());
	            o.setPrice(b.getPrice());
	            o.setInfo(b.getInfo());
	            
	            o.setOrederDate(new Date());
	            OrdersRepo.save(o);
	            BasketRepo.delete(b);
	         }         
	         return "fastfood/Payment";
	      }
	      return "fastfood/Payment";
	   }
	   public int checkUserPayInfo(String cardnum, String cardCompany,Customer customer) {
	      if(!cardnum.equals(customer.getCardNum())) {
	         return -1;
	      }
	      if(!cardCompany.equals(customer.getCardCompany())) {
	         return -2;
	      }
	      return 1; //??????
	   }
	   public int calcu_price(Iterable<Basket> basketList) {
	      int totalprice=0;
	      for(Basket b:basketList) {
	    	  
	         int price=b.getProductinfo().getPrice();
	         price=price*b.getPCount();
	         totalprice+=price;
	      }
	      
	      return totalprice;
	   }
	
}