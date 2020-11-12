import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

export const constantRoutes = [
  // {
  //   path: '/login',
  //   component: () => import('@/views/login/index'),
  //   hidden: true
  // },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '统计', icon: 'dashboard' }
    }]
  },
  {
    path: '/user',
    component: Layout,
    redirect: '/user/code/list',
    name: 'user',
    alwaysShow: true,
    meta: { title: '用户管理', icon: 'community' },
    children: [
      {
        path: 'code/list',
        name: 'list_code',
        component: () => import('@/views/code/list/index'),
        meta: { title: '激活码列表', icon: 'list' }
      }
    ]
  },
  {
    path: '/article',
    component: Layout,
    redirect: '/article/list',
    name: 'article',
    meta: { title: '文章', icon: 'community' },
    children: [
      {
        path: 'list',
        name: 'list_article',
        component: () => import('@/views/article/list/index'),
        meta: { title: '文章列表', icon: 'list' }
      },
      {
        path: 'type',
        name: 'type_article',
        component: () => import('@/views/article/type/index'),
        meta: { title: '文章类别', icon: 'type' }
      },
      {
        path: 'add_html',
        name: 'add_article_html',
        hidden: true,
        component: () => import('@/views/article/add/html'),
        meta: { title: '添加文章', icon: 'add' }
      },
      {
        path: 'add_md',
        name: 'add_article_md',
        hidden: true,
        component: () => import('@/views/article/add/md'),
        meta: { title: '添加文章', icon: 'add' }
      },
      {
        path: 'edit_html/:articleId',
        name: 'edit_article_html',
        hidden: true,
        component: () => import('@/views/article/edit/html'),
        meta: { title: '编辑文章', icon: 'edit' }
      },
      {
        path: 'edit_md/:articleId',
        name: 'edit_article_md',
        hidden: true,
        component: () => import('@/views/article/edit/md'),
        meta: { title: '编辑文章', icon: 'edit' }
      }
    ]
  },
  {
    path: '/news',
    component: Layout,
    redirect: '/news/list',
    name: 'news',
    alwaysShow: true,
    meta: { title: '资讯管理', icon: 'community' },
    children: [
      {
        path: 'list',
        name: 'list_news',
        component: () => import('@/views/news/list/index'),
        meta: { title: '资讯列表', icon: 'list' }
      },
      {
        path: 'add_html',
        name: 'add_news_html',
        hidden: true,
        component: () => import('@/views/news/add/html'),
        meta: { title: '添加资讯', icon: 'add' }
      },
      {
        path: 'add_md',
        name: 'add_news_md',
        hidden: true,
        component: () => import('@/views/news/add/md'),
        meta: { title: '添加资讯', icon: 'add' }
      },
      {
        path: 'edit_html/:newsId',
        name: 'edit_news_html',
        hidden: true,
        component: () => import('@/views/news/edit/html'),
        meta: { title: '编辑资讯', icon: 'edit' }
      },
      {
        path: 'edit_md/:newsId',
        name: 'edit_news_md',
        hidden: true,
        component: () => import('@/views/news/edit/md'),
        meta: { title: '编辑资讯', icon: 'edit' }
      }
    ]
  },
  {
    path: '/english',
    component: Layout,
    redirect: '/english/word/list',
    name: 'english',
    meta: { title: '英语', icon: 'community' },
    children: [
      // 单词
      {
        path: 'word/list',
        name: 'list_word',
        component: () => import('@/views/word/list/index'),
        meta: { title: '单词列表', icon: 'list' }
      },
      {
        path: 'word/add',
        name: 'add_word',
        hidden: true,
        component: () => import('@/views/word/add/index'),
        meta: { title: '添加单词', icon: 'add' }
      },
      {
        path: 'word/edit/:wordId',
        name: 'edit_word',
        hidden: true,
        component: () => import('@/views/word/edit/index'),
        meta: { title: '编辑文章', icon: 'edit' }
      },
      // 短语
      {
        path: 'phrase/list',
        name: 'list_phrase',
        component: () => import('@/views/phrase/list/index'),
        meta: { title: '短语列表', icon: 'list' }
      },
      {
        path: 'phrase/add',
        name: 'add_phrase',
        hidden: true,
        component: () => import('@/views/phrase/add/index'),
        meta: { title: '添加短语', icon: 'add' }
      },
      {
        path: 'phrase/edit/:phraseId',
        name: 'edit_phrase',
        hidden: true,
        component: () => import('@/views/phrase/edit/index'),
        meta: { title: '编辑短语', icon: 'edit' }
      },
      // 句子
      {
        path: 'sentence/list',
        name: 'list_sentence',
        component: () => import('@/views/sentence/list/index'),
        meta: { title: '句子列表', icon: 'list' }
      },
      {
        path: 'sentence/add',
        name: 'add_sentence',
        hidden: true,
        component: () => import('@/views/sentence/add/index'),
        meta: { title: '添加句子', icon: 'add' }
      },
      {
        path: 'sentence/edit/:sentenceId',
        name: 'edit_sentence',
        hidden: true,
        component: () => import('@/views/sentence/edit/index'),
        meta: { title: '编辑句子', icon: 'edit' }
      }
    ]
  },
  {
    path: '/library',
    component: Layout,
    redirect: '/library/paper/list',
    meta: { title: '题库管理', icon: 'community' },
    children: [
      {
        path: 'paper/list',
        name: 'list_paper',
        component: () => import('@/views/library/paper/index'),
        meta: { title: '试卷列表', icon: 'list' }
      },
      {
        path: ':paperId/question/list',
        name: 'list_question',
        hidden: true,
        component: () => import('@/views/library/question/list'),
        meta: { title: '问题列表', icon: 'list' }
      },
      {
        path: ':paperId/question/add',
        name: 'add_question',
        hidden: true,
        component: () => import('@/views/library/question/add'),
        meta: { title: '添加问题', icon: 'add' }
      },
      {
        path: ':paperId/question/edit/:questionId',
        name: 'edit_question',
        hidden: true,
        component: () => import('@/views/library/question/edit'),
        meta: { title: '修改问题', icon: 'edit' }
      },
      {
        path: 'paper/series',
        name: 'paper_series',
        component: () => import('@/views/library/type/index'),
        meta: { title: '题库类别', icon: 'type' }
      }
    ]
  },
  {
    path: '/book',
    component: Layout,
    redirect: '/book/list',
    meta: { title: '书籍', icon: 'community' },
    children: [
      {
        path: 'list',
        name: 'list_book',
        component: () => import('@/views/book/book/index'),
        meta: { title: '书籍列表', icon: 'list' }
      },
      {
        path: ':bookId/chapter/list',
        name: 'list_chapter',
        hidden: true,
        component: () => import('@/views/book/chapter/index'),
        meta: { title: '章节列表', icon: 'list' }
      },
      {
        path: ':bookId/chapter/:chapterId',
        name: 'edit_chapter',
        hidden: true,
        component: () => import('@/views/book/paragraph/index'),
        meta: { title: '段落编辑', icon: 'edit' }
      }
    ]
  },
  {
    path: '/math',
    component: Layout,
    redirect: '/math/list',
    meta: { title: '书籍', icon: 'community' },
    children: [
      {
        path: 'list',
        name: 'list_math',
        component: () => import('@/views/math/math/index'),
        meta: { title: '数学科目', icon: 'list' }
      },
      {
        path: ':mathId/unit/list',
        name: 'list_unit',
        hidden: true,
        component: () => import('@/views/math/unit/index'),
        meta: { title: '科目单元', icon: 'list' }
      },
      {
        path: ':mathId/unit/:unitId',
        name: 'edit_unit',
        hidden: true,
        component: () => import('@/views/math/concept/index'),
        meta: { title: '概念编辑', icon: 'edit' }
      }
    ]
  },
  {
    path: '/resource',
    component: Layout,
    redirect: '/resource/list',
    name: 'resource',
    meta: { title: '资源', icon: 'community' },
    children: [
      {
        path: 'list',
        name: 'list_resource',
        component: () => import('@/views/resource/list/index'),
        meta: { title: '资源列表', icon: 'list' }
      },
      {
        path: 'type',
        name: 'type_resource',
        component: () => import('@/views/resource/type/index'),
        meta: { title: '资源类别', icon: 'type' }
      }
    ]
  },
  {
    path: '/dynamic',
    component: Layout,
    redirect: '/dynamic/list',
    alwaysShow: true,
    name: 'dynamic',
    meta: { title: '杂项管理', icon: 'community' },
    children: [
      {
        path: 'list',
        name: 'list_dynamic',
        component: () => import('@/views/dynamic/list/index'),
        meta: { title: '动态内容列表', icon: 'list' }
      },
      {
        path: 'add_html',
        name: 'add_dynamic_html',
        hidden: true,
        component: () => import('@/views/dynamic/add/html'),
        meta: { title: '添加动态内容', icon: 'add' }
      },
      {
        path: 'edit_html/:dynamicId',
        name: 'edit_dynamic_html',
        hidden: true,
        component: () => import('@/views/dynamic/edit/html'),
        meta: { title: '编辑动态内容', icon: 'edit' }
      }
    ]
  },
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
