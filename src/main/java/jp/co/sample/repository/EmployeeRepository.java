package jp.co.sample.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Employee;

/**
 * employeesテーブルを操作するリポジトリ.
 * 
 * @author risa.nazato
 *
 */
@Repository
public class EmployeeRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * Employeeオブジェクトを生成するローマッパー.
	 */
	private static final RowMapper<Employee> EMPLOYEE_ROW_MAPPER = (rs, i) -> {
		Employee employee = new Employee();
		employee.setId(rs.getInt("id"));
		employee.setName(rs.getString("name"));
		employee.setImage(rs.getString("image"));
		employee.setGender(rs.getString("gender"));
		employee.setHireDate(rs.getDate("hireDate"));
		employee.setMailAddress(rs.getString("mailAddress"));
		employee.setZipCode(rs.getString("zipCode"));
		employee.setAddress(rs.getString("address"));
		employee.setTelephone(rs.getString("telephone"));
		employee.setSalary(rs.getInt("salary"));
		employee.setCharacteristics(rs.getString("characteristics"));
		employee.setDependentsCount(rs.getInt("dependentsCount"));
		return employee;
	};

	/**
	 * 従業員一覧情報を入社日順で取得する.
	 * 
	 * @return 従業員一覧
	 */
	public List<Employee> findAll() {
		String sql = "SELECT id,name,image,gender,hireDate,mailAddress,zipCode,address,telephone,salary,characteristics,dependentsCount FROM employees ORDER BY hireDate DESC;";

		List<Employee> employeeList = template.query(sql, EMPLOYEE_ROW_MAPPER);
		return employeeList;
	}

	/**
	 * 主キーから従業員情報を取得する.
	 * 
	 * @param id ID
	 * @return 検索された従業員情報(存在しない場合例外が発生)
	 */
	public Employee load(Integer id) {
		String sql = "SELECT id,name,image,gender,hireDate,mailAddress,zipCode,address,telephone,salary,characteristics,dependentsCount FROM employees WHERE id=:id;";

		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

		Employee employee = template.queryForObject(sql, param, EMPLOYEE_ROW_MAPPER);

		return employee;
	}

	/**
	 * 従業員情報を変更する.
	 * 
	 * @param employee 従業員
	 */
	public void update(Employee employee) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(employee);

		String insertSql = "UPDATE employees SET dependentsCount=:dependentsCount";
		template.update(insertSql, param);
	}
}