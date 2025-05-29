/*
 * Copyright 2011 MOPAS(Ministry of Public Administration and Security).
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

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 * Mapper Class for processing sample data
 *
 * @author eGovFrame Center
 * @since 2014.01.24
 * @version 1.0
 * @see
 * 
 *      <pre>
 *  == Modification Information ==
 *
 * &#64;  Modified Date      Modifier    Description
 * &#64; --------------      ---------   -------------------------------
 * &#64; 2009.03.16                      Initial Commit
 *
 * </pre>
 */
@Mapper("sampleMapper")
public interface SampleMapper {

	/**
	 * Insert
	 * 
	 * @param vo - Sample Value Object SampleVO
	 * @return Generated ID
	 * @exception Exception
	 */
	void insertSample(SampleVO vo) throws Exception;

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
