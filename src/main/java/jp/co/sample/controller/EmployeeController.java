package jp.co.sample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Employee;
import jp.co.sample.form.UpdateEmployeeForm;
import jp.co.sample.service.EmployeeService;

/**
 * 従業員関連機能の処理の制御を行うコントローラ
 * 
 * @author risa.nazato
 *
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {
	@Autowired
	private EmployeeService employService;

	/**
	 * 従業員一覧を表示.
	 * 
	 * @param model モデル
	 * @return 従業員一覧画面に遷移.
	 */
	@RequestMapping("/showList")
	public String showList(Model model) {
		model.addAttribute("employeeList", employService.showList());

		return "employee/list.html";
	}

	@ModelAttribute
	public UpdateEmployeeForm setUpdateEmployeeForm() {
		UpdateEmployeeForm updateEmployeeForm = new UpdateEmployeeForm();
		return updateEmployeeForm;
	}

	/**
	 * 
	 * 従業員情報を受け取り、詳細画面に遷移.
	 * 
	 * @param id    ID
	 * @param model モデル
	 * @return 従業員詳細画面
	 */
	@RequestMapping("/showDetail")
	public String showDetail(String id, Model model) {
		model.addAttribute("employee", employService.showDetail(Integer.parseInt(id)));
		return "employee/detail.html";
	}

	/**
	 * 
	 * 従業員詳細を更新.
	 * 
	 * @param form  フォーム
	 * @param model モデル
	 * @return 従業員一覧画面にリダイレクト
	 */
	@RequestMapping("/update")
	public String update(UpdateEmployeeForm form) {
		Employee employee = employService.showDetail((Integer.valueOf(form.getId())));
		employee.setDependentsCount((Integer.valueOf(form.getDependentsCount())));
		employService.update(employee);
		return "redirect:/employee/showList";
	}
}
