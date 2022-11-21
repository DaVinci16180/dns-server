import React, { useState, useReducer, useEffect } from "react";
import axios from "axios";
import toastr from 'toastr'
import 'toastr/build/toastr.min.css'

export default function ServerManage() {

    const [amount, setAmount] = useState('')
    const [domain, setDomain] = useState('')
    const [ip, setIp] = useState('')
    const [list, setList] = useState([])

    useEffect(() => {
      axios
        .get('http://localhost:8080/api/all')
        .then(res => {
            setList(res.data)
        })
    }, [])

    const changeAmount = (e) => {
        setAmount(e.target.value)
    }

    const changeDomain = (e) => {
        setDomain(e.target.value)
    }

    const changeIp = (e) => {
        setIp(e.target.value)
    }

    const massCreate = () => {
        axios
            .get(`http://localhost:8080/api/massCreate/${amount}`)
            .then(() => {
                toastr.options = {
                    positionClass : 'toast-top-right',
                    hideDuration: 300,
                    timeOut: 3000
                }

                toastr.clear()
                setTimeout(() => toastr.success('Success'), 0)

                axios
                    .get('http://localhost:8080/api/all')
                    .then(res => {
                        setList(res.data)
                    })
                
                setAmount('')
            })

    }

    const createDomain = () => {
        const options = {
            url: 'http://localhost:8080/api/create',
            method: 'POST',
            data: {
                domain,
                ip
            }
        };
        
        axios(options)
            .then(() => {
                toastr.options = {
                    positionClass : 'toast-top-right',
                    hideDuration: 300,
                    timeOut: 3000
                }
                toastr.clear()
                setTimeout(() => toastr.success('Success'), 0)

                axios
                    .get('http://localhost:8080/api/all')
                    .then(res => {
                        setList(res.data)
                    })

                setDomain('')
                setIp('')
            })

    }

    const remove = key => {
        axios
            .delete(`http://localhost:8080/api/delete/${key}`)
            .then(() => {
                toastr.options = {
                    positionClass : 'toast-top-right',
                    hideDuration: 300,
                    timeOut: 3000
                }

                toastr.clear()
                setTimeout(() => toastr.success('Success'), 0)

                axios
                    .get('http://localhost:8080/api/all')
                    .then(res => {
                        setList(res.data)
                    })
            })
    }
        
    return (
        <div className="server-manage">
            <div className="form">
                <div className="form-row">
                    <label>Amount</label>
                    <input name="amount" onChange={ changeAmount } value={ amount } />
                </div>
                
                <button onClick={ massCreate } >Create</button>
            </div>

            <div className="form">
                <div className="form-row">
                    <label>Domain</label>
                    <input name="domain" onChange={ changeDomain } value={ domain } />
                </div>
                <div className="form-row">
                    <label>Ip Address</label>
                    <input name="domain" onChange={ changeIp } value={ ip } />
                </div>
                
                <button onClick={ createDomain } >Create</button>
            </div>
            
            <div className="table">
                <table>
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Domain</th>
                            <th>Ip Address</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        { list.map((row, index) => (
                            <tr key={ row.key }>
                                <td>{ index + 1 }</td>
                                <td onClick={ () => {
                                    setDomain(row.key)
                                    setIp(row.value)
                                } }>{ row.key }</td>
                                <td>{ row.value }</td>
                                <td className="remove" onClick={ () => remove(row.key) } >Remover</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    )
}