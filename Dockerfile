FROM ruivieira:ijava

RUN wget -O $HOME/ml-latest-small.zip http://files.grouplens.org/datasets/movielens/ml-latest-small.zip \
    && cd $HOME \
    && unzip ml-latest-small.zip

USER $NB_USER

# Launch the notebook server
WORKDIR $HOME
CMD ["jupyter", "notebook", "--ip", "0.0.0.0"]