import React from "react";
import { Card, CardContent, Typography, Avatar, Grid, IconButton } from "@mui/material";
import { Edit, Delete } from "@mui/icons-material";

class UserItem extends React.Component {
    render() {
        const { user, onDelete, onEdit } = this.props;

        return (
            <Card sx={{ my: 2, p: 2, borderRadius: 3, boxShadow: 3 }}>
                <CardContent>
                    <Grid container alignItems="center" spacing={2}>
                        <Grid item>
                            <Avatar sx={{ bgcolor: "#90caf9" }}>
                                {user.name?.charAt(0).toUpperCase() || "U"}
                            </Avatar>
                        </Grid>
                        <Grid item xs>
                            <Typography variant="h6">{user.name}</Typography>
                            <Typography variant="body2" color="text.secondary">{user.username}</Typography>
                        </Grid>
                        <Grid item>
                            <IconButton color="primary" onClick={() => onEdit(user)}>
                                <Edit />
                            </IconButton>
                            <IconButton color="error" onClick={() => onDelete(user.id)}>
                                <Delete />
                            </IconButton>
                        </Grid>
                    </Grid>
                </CardContent>
            </Card>
        );
    }
}

export default UserItem;
