package com.yr.entitys.menu;

import com.yr.common.entity.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 菜单
 * @author Administrator
 *
 */
@Entity
@Table(name = "menu")
public class Menu extends BaseEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;//菜单id
	private String name;//菜单名称
	private String pic;//菜单图标
	@Column(nullable = false)//并且是不为null
	private Integer pid;//菜单父id
	@Column(unique = true, nullable = false)//设置为唯一约束，并且是不为null
	private String url;//
	@Column(nullable = false)//并且是不为null
	private String method;//url的请求方式（要控制大写）

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	@Override
	public String toString() {
		return "Menu{" +
				"id=" + id +
				", name='" + name + '\'' +
				", pic='" + pic + '\'' +
				", pid=" + pid +
				", url='" + url + '\'' +
				", method='" + method + '\'' +
				'}';
	}
}
