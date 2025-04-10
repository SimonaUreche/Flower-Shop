import React, { useState, useEffect } from "react";
import { Dialog, DialogTitle, DialogContent, TextField, Button, MenuItem} from "@mui/material";

const roles = ["ADMIN", "CLIENT", "LIVRATOR"];

function EditUserForm({ user, open, onClose, onSave }) { //componenta functionala cu 4 props
    const [formData, setFormData] = useState({ ...user }); //starea initiala = date user = ceea ce modificam in formular


    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({ ...prev, [name]: value }));
    };

    const handleSubmit = () => {
        onSave(formData);
    };

    return (
        <Dialog open={open} onClose={onClose}>
            <DialogTitle>Editează utilizator</DialogTitle>
            <DialogContent>
                <TextField
                    fullWidth
                    margin="normal"
                    name="name"
                    label="Nume"
                    value={formData.name || ""}
                    onChange={handleChange} //permite modificarea campurilor
                />
                <TextField
                    fullWidth
                    margin="normal"
                    name="email"
                    label="Email"
                    value={formData.email || ""}
                    onChange={handleChange}
                />
                <TextField
                    fullWidth
                    margin="normal"
                    name="phoneNumber"
                    label="Telefon"
                    value={formData.phoneNumber || ""}
                    onChange={handleChange}
                />
                <TextField
                    fullWidth
                    margin="normal"
                    name="address"
                    label="Adresă"
                    value={formData.address || ""}
                    onChange={handleChange}
                />
                <TextField
                    select
                    fullWidth
                    margin="normal"
                    name="role"
                    label="Rol"
                    value={formData.role || ""}
                    onChange={handleChange}
                >
                    {roles.map((r) => (
                        <MenuItem key={r} value={r}>
                            {r}
                        </MenuItem>
                    ))}
                </TextField>
                <Button variant="contained" onClick={handleSubmit} sx={{ mt: 2 }}>
                    Salvează
                </Button>
            </DialogContent>
        </Dialog>
    );
}

export default EditUserForm;
