package com.example.demo.controllers;

import com.example.demo.models.Recipe;
import com.example.demo.models.RecipeDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ShowRecipe", value = "/show-recipe")
public class ShowRecipeController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Пользователь обратился к адресу /create-recipe, перенаправляем его на страницу create_recipe.jsp
        //Без смены url
        String user = request.getParameter("userPref");
        String recipe = request.getParameter("recipePref");

        if((!user.equals("null"))&&(!recipe.equals("null"))){
            System.out.println("doGet:" +user +" "+recipe);
            List<Recipe> recipeList = RecipeDAO.getInstance().getRecipes(user,recipe);
            request.setAttribute("recipes", recipeList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("all_recipes.jsp");
            dispatcher.forward(request, response);
        }

        else{
            List<Recipe> recipeList = RecipeDAO.getInstance().allRecipes();
            request.setAttribute("recipes", recipeList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("all_recipes.jsp");
            dispatcher.forward(request, response);
        }
    }
}
