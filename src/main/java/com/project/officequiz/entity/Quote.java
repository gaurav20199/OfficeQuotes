package com.project.officequiz.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Quote {
    
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String quote;
	private int answerOption;

	@OneToMany(mappedBy = "quote",cascade = CascadeType.PERSIST)
	private List<Options> options;

	public String getQuote() {
		return quote;
	}

	public void setQuote(String quote) {
		this.quote = quote;
	}

	public int getAnswerOption() {
		return answerOption;
	}
	public void setAnswerOption(int answerOption) {
		this.answerOption = answerOption;
	}

	public List<Options> getOptions() {
		return options;
	}

	public void setOptions(List<Options> options) {
		this.options = options;
	}
}
