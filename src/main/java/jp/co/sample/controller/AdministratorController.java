package jp.co.sample.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Administrator;
import jp.co.sample.form.InsertAdministratorForm;
import jp.co.sample.form.LoginForm;
import jp.co.sample.service.AdministratorService;

/**
 * 管理者関連機能の処理の制御を行うコントローラ.
 * 
 * @author risa.nazato
 *
 */
@Controller
@RequestMapping("/")
public class AdministratorController {
	@Autowired
	private AdministratorService administratorservice;

	@Autowired
	private HttpSession session;

	@ModelAttribute
	public InsertAdministratorForm setUpInsertAdministratorForm() {
		InsertAdministratorForm insertadministratorform = new InsertAdministratorForm();
		return insertadministratorform;
	}

	/**
	 * 管理者情報登録画面に遷移する.
	 * 
	 * @return 管理者情報登録画面
	 */
	@RequestMapping("/toInsert")
	public String toInsert() {
		return "administrator/insert.html";
	}

	/**
	 * 管理者情報を登録.
	 * 
	 * @param form フォーム
	 * @return ログイン画面にリダイレクト
	 */
	@RequestMapping("/insert")
	public String insert(InsertAdministratorForm form) {
		Administrator administrator = new Administrator();
		BeanUtils.copyProperties(form, administrator);
		administratorservice.insert(administrator);
		return "redirect:/";
	}

	@ModelAttribute
	public LoginForm setUpLoginForm() {
		LoginForm loginform = new LoginForm();
		return loginform;
	}

	/**
	 * ログイン画面に遷移する.
	 * 
	 * @return
	 */
	@RequestMapping("/")
	public String toLogin() {
		return "administrator/login.html";
	}

	/**
	 * ログイン後のページ表示.
	 * 
	 * @param form フォーム
	 * @return 通常は従業員情報一覧(ログイン失敗時はログイン画面)
	 */
	@RequestMapping("/login")
	public String login(LoginForm form, Model model) {

		Administrator adminObject = administratorservice.login(form.mailAddress, form.password);
		if (adminObject == null) {
			model.addAttribute("message", "メールアドレスまたはパスワードが不正です");
			return "administrator/login.html";
		}
		session.setAttribute("administratorName", adminObject.getName());
		return "forward:/employee/showList";
	}

	/**
	 * 
	 * ログアウト処理.
	 * 
	 * @return ログイン画面にリダイレクト
	 */
	@RequestMapping("/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/";
	}

}
