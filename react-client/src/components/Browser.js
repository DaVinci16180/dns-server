import axios from "axios";
import React, { useState } from "react";
import { AiOutlineArrowLeft, AiOutlineArrowRight, AiOutlineSearch } from 'react-icons/ai'
import { IoMdRefresh } from 'react-icons/io'
import { TbError404 } from 'react-icons/tb'

export default function Browser() {

    const [search, setSearch] = useState('')
    const [ip, setIp] = useState('Welcome!')
    const [error, setError] = useState(false)

    const handleChange = event => {
        setSearch(event.target.value)
    }

    const fetch = event => {
        event.preventDefault()
        axios
            .get(`http://localhost:8080/api/fetch/${search}`)
            .then(response => {
                setIp(response.data)
                setError(false)
            })
            .catch(err => {
                setError(true)
            })
    }

    return (
        <div className="browser-window">
            <div className="window-header">
                <div className="window-buttons"></div>
                Browser
                <div style={{width: '2rem'}}></div>
            </div>
            <form className="browser-header">
                <div className="buttons"> 
                    <AiOutlineArrowLeft />
                    <AiOutlineArrowRight />
                    <IoMdRefresh />
                </div>
                <input value={search} onChange={handleChange} />
                <button className="search-button" onClick={fetch} type="submit" >
                    <AiOutlineSearch/>
                </button>
            </form>
            <div className="browser-content">
                { error && 
                <div className="error">
                    <TbError404 style={{ fontSize: '2rem' }} />
                    <span className="error-msg">Not Found!</span>
                </div>
                }

                { !error && <span className="ip-address">{ ip }</span> }
            </div>
        </div>
    )
}