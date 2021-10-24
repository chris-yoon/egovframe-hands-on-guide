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
package egovframework.example.sample.service.impl;

import java.util.List;

import egovframework.example.sample.service.SampleDefaultVO;
import egovframework.example.sample.service.SampleVO;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;

import org.springframework.stereotype.Repository;

/**
 * @Class Name : SampleDAO.java
 * @Description : Sample DAO Class
 * @Modification Information
 * @
 * @  Modified Date      Modifier    Description
 * @ --------------      ---------   -------------------------------
 * @ 2009.03.16                      Initial Commit
 *
 * @author eGovFrame Runtime Environment Team
 * @since 2009. 03.16
 * @version 1.0
 * @see
 *
 *  Copyright (C) by MOPAS All right reserved.
 */

@Repository("sampleDAO")
public class SampleDAO extends EgovAbstractDAO {

	/**
	 * Insert
	 * @param vo - Sample Value Object SampleVO
	 * @return Generated ID
	 * @exception Exception
	 */
	public String insertSample(SampleVO vo) throws Exception {
		return (String) insert("sampleDAO.insertSample", vo);
	}

	/**
	 * Update
	 * @param vo - Sample Value Object SampleVO
	 * @return void type
	 * @exception Exception
	 */
	public void updateSample(SampleVO vo) throws Exception {
		update("sampleDAO.updateSample", vo);
	}

	/**
	 * Delete
	 * @param vo - Sample Value Object SampleVO
	 * @return void type
	 * @exception Exception
	 */
	public void deleteSample(SampleVO vo) throws Exception {
		delete("sampleDAO.deleteSample", vo);
	}

	/**
	 * Select single row
	 * @param vo - Value Object SampleVO
	 * @return Sample Object
	 * @exception Exception
	 */
	public SampleVO selectSample(SampleVO vo) throws Exception {
		return (SampleVO) select("sampleDAO.selectSample", vo);
	}

	/**
	 * Select multiple rows
	 * @param searchVO - Search Condition
	 * @return Sample List
	 * @exception Exception
	 */
	public List<?> selectSampleList(SampleDefaultVO searchVO) throws Exception {
		return list("sampleDAO.selectSampleList", searchVO);
	}

	/**
	 * Count number of rows
	 * @param searchVO - Search Condition
	 * @return Count of Samples
	 * @exception
	 */
	public int selectSampleListTotCnt(SampleDefaultVO searchVO) {
		return (Integer) select("sampleDAO.selectSampleListTotCnt", searchVO);
	}

}
