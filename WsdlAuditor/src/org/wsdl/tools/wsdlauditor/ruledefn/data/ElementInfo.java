/*
* Copyright 2010 Saikiran Daripelli(saikirandaripelli@gmail.com). All rights reserved.
*
*Redistribution and use in source and binary forms, with or without modification, are
*permitted provided that the following conditions are met:
*
*  1. Redistributions of source code must retain the above copyright notice, this list of
*     conditions and the following disclaimer.
*
*  2. Redistributions in binary form must reproduce the above copyright notice, this list
*     of conditions and the following disclaimer in the documentation and/or other materials
*     provided with the distribution.
*
*THIS SOFTWARE IS PROVIDED BY Saikiran Daripelli(saikirandaripelli@gmail.com) ``AS IS'' 
*AND ANY EXPRESS OR IMPLIED
*WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
*FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL Saikiran Daripelli
*(saikirandaripelli@gmail.com) OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, 
* SPECIAL, EXEMPLARY, OR
*CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
*SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
*ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
*NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
*ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
*The views and conclusions contained in the software and documentation are those of the
*authors and should not be interpreted as representing official policies, either expressed
*or implied, of Saikiran Daripelli(saikirandaripelli@gmail.com).
*/
package org.wsdl.tools.wsdlauditor.ruledefn.data;

import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Element;
import org.wsdl.tools.wsdlauditor.ruledefn.data.enums.SchemaTypes;
import org.wsdl.tools.wsdlauditor.ruledefn.utils.XmlUtil;

/**
 * The Class ElementInfo.
 */
public class ElementInfo {
	
	/** The element. */
	private Element element;
	
	/** The complete path. */
	private List<ElementInfo> completePath = new LinkedList<ElementInfo>();
	
	/** The position. */
	private int position;
	
	/** The type. */
	private SchemaTypes type;
	
	/** The number of peers. */
	private int numberOfPeers;

	/**
	 * Gets the element.
	 * 
	 * @return the element
	 */
	public Element getElement() {
		return element;
	}

	/**
	 * Sets the element.
	 * 
	 * @param element
	 *            the new element
	 */
	public void setElement(Element element) {
		this.element = element;
	}

	/**
	 * Gets the complete path.
	 * 
	 * @return the complete path
	 */
	public List<ElementInfo> getCompletePath() {
		return completePath;
	}

	/**
	 * Sets the complete path.
	 * 
	 * @param completePath
	 *            the new complete path
	 */
	public void setCompletePath(List<ElementInfo> completePath) {
		this.completePath = completePath;
	}

	/**
	 * Gets the position.
	 * 
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((completePath == null) ? 0 : completePath.hashCode());
		result = prime * result + ((element == null) ? 0 : element.hashCode());
		result = prime * result + numberOfPeers;
		result = prime * result + position;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ElementInfo other = (ElementInfo) obj;
		if (completePath == null) {
			if (other.completePath != null)
				return false;
		}
		if (element == null) {
			if (other.element != null)
				return false;
		} else if (!XmlUtil.areElementsEqual(element, other.element))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	
	/**
	 * Sets the position.
	 * 
	 * @param position
	 *            the new position
	 */
	public void setPosition(int position) {
		this.position = position;
	}

	/**
	 * Gets the type.
	 * 
	 * @return the type
	 */
	public SchemaTypes getType() {
		return type;
	}

	/**
	 * Sets the type.
	 * 
	 * @param type
	 *            the new type
	 */
	public void setType(SchemaTypes type) {
		this.type = type;
	}

	/**
	 * Gets the number of peers.
	 * 
	 * @return the number of peers
	 */
	public int getNumberOfPeers() {
		return numberOfPeers;
	}

	/**
	 * Sets the number of peers.
	 * 
	 * @param numberOfPeers
	 *            the new number of peers
	 */
	public void setNumberOfPeers(int numberOfPeers) {
		this.numberOfPeers = numberOfPeers;
	}

}
