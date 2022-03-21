import React, { useState } from "react";
import { styled, alpha } from "@mui/material/styles";
import Box from "@mui/material/Box";
import InputBase from "@mui/material/InputBase";
import SearchIcon from "@mui/icons-material/Search";

const Search = styled("div")(({ theme }) => ({
  position: "relative",
  borderRadius: theme.shape.borderRadius,
  backgroundColor: alpha(theme.palette.common.white, 0.15),
  "&:hover": {
    backgroundColor: alpha(theme.palette.common.white, 0.25),
  },
  marginLeft: 0,
  width: "100%",
  [theme.breakpoints.up("sm")]: {
    marginLeft: theme.spacing(1),
    width: "auto",
  },
}));

const SearchIconWrapper = styled("div")(({ theme }) => ({
  padding: theme.spacing(0, 2),
  height: "100%",
  position: "absolute",
  pointerEvents: "none",
  display: "flex",
  alignItems: "center",
  justifyContent: "center",
}));

const StyledInputBase = styled(InputBase)(({ theme }) => ({
    color: "inherit",
    "& .MuiInputBase-input": {
      padding: theme.spacing(1, 1, 1, 0),
      // vertical padding + font size from searchIcon
      paddingLeft: "calc(1em + ${theme.spacing(4)})",
      transition: theme.transitions.create("width"),
      width: "100%",
      [theme.breakpoints.up("sm")]: {
        width: "12ch",
        "&:focus": {
          width: "20ch",
        },
      },
    },
  }));

interface food {
  name: string;
  calories: number;
  fat: number;
  carbs: number;
  protein: number;
}

const originalRows: food[] = [
  { name: "Pizza", calories: 200, fat: 6.0, carbs: 24, protein: 4.0 },
  { name: "Hot Dog", calories: 300, fat: 6.0, carbs: 24, protein: 4.0 },
  { name: "Burger", calories: 400, fat: 6.0, carbs: 24, protein: 4.0 },
  { name: "Hamburger", calories: 500, fat: 6.0, carbs: 24, protein: 4.0 },
  { name: "Fries", calories: 600, fat: 6.0, carbs: 24, protein: 4.0 },
  { name: "Ice Cream", calories: 700, fat: 6.0, carbs: 24, protein: 4.0 }
];
  

export default function SearchBar() {
  const [rows, setRows] = useState<food[]>(originalRows);
  const [searched, setSearched] = useState<string>("");

  const requestSearch = (searchedVal: string) => {
    //const filteredRows = originalRows.filter((row) => {
      //return row.name.toLowerCase().includes(searchedVal.toLowerCase());
    //});
    //setRows(filteredRows);
  };

    
    return (
      <Box sx={{ flexGrow: 1 }}>

            <Search>
              <SearchIconWrapper>
                <SearchIcon />
              </SearchIconWrapper>
              <StyledInputBase
                placeholder="Search…"
                inputProps={{ "aria-label": "search" }}
                onChange={(e) => requestSearch(e.target.value)}
              />
            </Search>
      </Box>
    );
  }