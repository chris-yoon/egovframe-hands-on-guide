/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package egovframework.example.sample.service;

import java.util.List;

/**
 * @Class Name : EgovSampleService.java
 * @Description : EgovSampleService Class
 * @Modification Information
 * @
 *   @ Modified Date Modifier Description
 *   @ -------------- --------- -------------------------------
 *   @ 2009.03.16 Initial Commit
 *
 * @author eGovFrame Runtime Environment Team
 * @since 2009. 03.16
 * @version 1.0
 * @see
 *
 *      Copyright (C) by MOPAS All right reserved.
 */
public interface EgovSampleService {

	/**
	 * Insert
	 * 
	 * @param vo - Sample Value Object SampleVO
	 * @return Generated ID
	 * @exception Exception
	 */
	String insertSample(SampleVO vo) throws Exception;

	/**
	 * Update
	 * 
	 * @param vo - Sample Value Object SampleVO
	 * @return void type
	 * @exception Exception
	 */
	void updateSample(SampleVO vo) throws Exception;

	/**
	 * Delete
	 * 
	 * @param vo - Sample Value Object SampleVO
	 * @return void type
	 * @exception Exception
	 */
	void deleteSample(SampleVO vo) throws Exception;

	/**
	 * Select single row
	 * 
	 * @param vo - Value Object SampleVO
	 * @return Sample Object
	 * @exception Exception
	 */
	SampleVO selectSample(SampleVO vo) throws Exception;

	/**
	 * Select multiple rows
	 * 
	 * @param searchVO - Search Condition
	 * @return Sample Object List
	 * @exception Exception
	 */
	List<?> selectSampleList(SampleDefaultVO searchVO) throws Exception;

	/**
	 * Count number of rows
	 * 
	 * @param searchVO - Search Condition
	 * @return Number of Samples
	 * @exception
	 */
	int selectSampleListTotCnt(SampleDefaultVO searchVO);

}
