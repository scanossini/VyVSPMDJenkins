package test.testfinal;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pages.DataPage;
import pages.FinishPage;
import pages.OverView;
import pages.LoginPage;
import pages.ProductPageButtonRandom;
import pages.ProductPageSelected;
import pages.ProductPageWithItems;
import pages.ProfilePage;
import test.BaseTest;

public class TestFinal extends BaseTest {

	public LoginPage loginPage;

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void VerifyMyCart() {
		//loggeo
		loginPage = new LoginPage();
		loginPage.inputUserName().write("standard_user");
		loginPage.inputUserPassword().write("secret_sauce");
		loginPage.btnLogin().click();

		//selecciono un boton random
		ProductPageButtonRandom productPageRandom = new ProductPageButtonRandom();
		productPageRandom.btnRandom().click();

		//pagina con item seleccionado y checkout
		ProductPageSelected productPageSelected = new ProductPageSelected();
		productPageSelected.goToMyCart().click();
		
		//clickea el checkout
		ProductPageWithItems payPage = new ProductPageWithItems();
		payPage.btnCheckOut().click();
		
		//busca el perfil
		this.driver.get("https://www.fakepersongenerator.com/Index/generate");
		ProfilePage profilePage = new ProfilePage();
		String name = profilePage.name().split("\\s+")[0];
		String lastName = profilePage.name().split("\\s+")[2];
		String zip = profilePage.zip().split(",")[2];
		
		//completa los campos
		this.driver.navigate().back();
		try{Thread.sleep(3000);}
		catch(Exception e) {e.printStackTrace();}
		DataPage dataPage = new DataPage();
		dataPage.inputFirstName().write(name);;
		dataPage.inputLastName().write(lastName);
		dataPage.inputPostalCode().write(zip);
		dataPage.btnContinue().click();
		
		//boton Finish
		OverView overViewPage = new OverView();
		overViewPage.btnFinish().click();
		
		//Verifo los mensajes
		FinishPage finishPage = new FinishPage();
		Assert.assertEquals(finishPage.lblThanks().text(), "THANK YOU FOR YOUR ORDER");
		Assert.assertEquals(finishPage.lblMessege().text(), "Your order has been dispatched, and will arrive just as fast as the pony can get there!");
	}

	@After
	public void tearDown() {
		this.finalize();
	}
}